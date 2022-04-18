package com.yash.project.repo;
import com.yash.project.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.User;

public interface UserRepo extends JpaRepository<User,Integer> {
User findByEmail(String email);

}
