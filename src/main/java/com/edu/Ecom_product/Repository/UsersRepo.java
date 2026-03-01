package com.edu.Ecom_product.Repository;

import com.edu.Ecom_product.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {
    public Users findByUserName(String username);
}
