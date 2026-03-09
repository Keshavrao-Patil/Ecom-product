package com.edu.Ecom_product.Service;

import com.edu.Ecom_product.Model.Users;
import com.edu.Ecom_product.Repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);

    public Users registerUser(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    public String verifyUser(Users users) {
        //UserDetails userDetails=myUserDetailsService.loadUserByUsername(users.getUserName());

       /* Users user=usersRepo.findByUserName(users.getUserName());
        if(user==null){
            return "User Not Found";
        }*/

        Authentication authentication=authManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUserName(),users.getPassword()));
      // return authentication.isAuthenticated()?"Success":"Login Failed !!";
       if(authentication.isAuthenticated()){
           log.info("Login Success");
           return jwtService.generateToken(users.getUserName());
       }else{
           log.info("Login Failed username or password is incorrect");
           return "Login Failed UserName or Password is Incorrect";
       }
    }
}
