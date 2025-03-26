package com.tasktrackingsystem.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tasktrackingsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByEmail(String email);

    User findByUsername(String username);
    
    Optional<User> findById(Long userid);
    
    User findByPhonenumber(Long phonenumber);
        
}
