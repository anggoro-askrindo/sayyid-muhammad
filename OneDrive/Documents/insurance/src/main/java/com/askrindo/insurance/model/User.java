package com.askrindo.insurance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID idUser;
    
    private String username;
    private String password;
    private String fullname;
    private String email;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "r_user_roles", 
        joinColumns = @JoinColumn(name = "id_user"), 
        inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private Set<Role> roles;


}
