package com.edu.Ecom_product.Service;

import com.edu.Ecom_product.Model.UserPrincipal;
import com.edu.Ecom_product.Model.Users;
import com.edu.Ecom_product.Repository.UsersRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepo.findByUserName(username);
        if(users==null){
            log.info("User not exists");
            throw new UsernameNotFoundException("" +
                    "USER NOT FOUND"+users.getUserName());
        }
        return new UserPrincipal(users);
    }


}
