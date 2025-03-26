package com.tasktrackingsystem.entity;


import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "tb_user")
@Data
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name="email", unique=true)
    private String email;

    @Column(name="password", unique=true)
    private String password;
    
    private String username;
    
    @Column(name="phonenumber", unique=true)
    private Long phonenumber; 
    
    @Lob     
    private byte[] profilePicture;

    private String role;   
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role)); 
      }
    
}
    
