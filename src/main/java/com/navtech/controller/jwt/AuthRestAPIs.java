package com.navtech.controller.jwt;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navtech.jwt.message.request.EmployeeProfileForm;
import com.navtech.jwt.message.request.LoginForm;
import com.navtech.jwt.message.request.SignUpForm;
import com.navtech.jwt.message.response.JwtResponse;
import com.navtech.jwt.security.jwt.JwtProvider;
import com.navtech.model.authenticate.Role;
import com.navtech.model.authenticate.RoleName;
import com.navtech.model.authenticate.User;
import com.navtech.model.management.EmployeeProfile;
import com.navtech.repository.jwt.RoleRepository;
import com.navtech.repository.jwt.UserRepository;
import com.navtech.repository.management.employprofile.EmployRepository;
 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/auth")
public class AuthRestAPIs {
 
    @Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    UserRepository userRepository;
 
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    EmployRepository empProfileRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtProvider jwtProvider;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
    	/**Create an "Authentication" object and give <username> and <password> as parameters**/
        Authentication authentication = authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
 
        /***set authentication object***/
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        /** Generate Jwt Token  through authentication object **/
        String jwt = jwtProvider.generateJwtToken(authentication);
        
        /** return jwt token **/
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) 
    {
    	try {
    		System.out.println(signUpRequest);
    		System.out.println("Password ::: "+signUpRequest.getPassword());
    		System.out.println("Roles ::: "+signUpRequest.getRole());
			/*
			 * "signUpRequest" is an object and contains JSON request details 
			 */
			
			/** Condition to check user name is already existed or not  before signup **/
			if(userRepository.existsByUsername(signUpRequest.getUsername())) 
			{
			    return new ResponseEntity<String>("Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
			}
 
			/** Condition to check email is already existed or not  before signup **/
			if(userRepository.existsByEmail(signUpRequest.getEmail())) 
			{
			    return new ResponseEntity<String>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
			}
 
			/** Creating user's account **/
			User user = new User(signUpRequest.getFirstname(), signUpRequest.getUsername(), 
					             signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
 
			/**Get roles list from "signUpRequest" object**/
			System.out.println("User Role size ::: "+signUpRequest.getRole()+" :::::::::: "+signUpRequest.getRole().size());
			EmployeeProfile empProfileForm = new EmployeeProfile();
			
			empProfileForm.setFirstName(signUpRequest.getFirstname());
			empProfileForm.setUsername(signUpRequest.getUsername());
			
			Set<String> ss = new HashSet<String>();
			if(userRepository.findAll().size() == 0)
			{
				System.out.println("AuthRestAPIs.registerUser(size) zero");
				if(signUpRequest.getRole().size() == 0)
				{
					ss.add("admin");
					signUpRequest.setRole(ss);
				}
				else
				{
					//Update roles
				}
			}
			else
			{
				System.out.println("AuthRestAPIs.registerUser(size) greaterthan zero");
				if(signUpRequest.getRole().size() == 0)
				{
					ss.add("user");
					signUpRequest.setRole(ss);
				}
				else
				{
					//Update roles
				}
			}
			
			Set<String> strRoles = signUpRequest.getRole();
			
			/**Create a new Set object with Role as generarics**/
			Set<Role> roles = new HashSet<>();
 
			/** Do for loop to get roles **/
			strRoles.forEach(role -> 
			{
			  switch(role) 
			  {
			  case "admin":
					Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			    roles.add(adminRole);
			    break;
			  case "pm":
			        Role pmRole = roleRepository.findByName(RoleName.ROLE_PM).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			        roles.add(pmRole);                
			    break;
			  default:
			      Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			      roles.add(userRole);              
			  }
			});
			
			/** Set given roles to user object **/
			user.setRoles(roles);
			
			userRepository.save(user);                    //Save User
			empProfileRepository.save(empProfileForm);    //Save EmployProfile
		}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	return ResponseEntity.ok().body("User registered successfully!");
    }
}