package com.dart.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;


@Entity
@Table(name = "\"dart_users\"") // Escaping "user" as an identifier
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    private String address;

    @Column(unique = true)
    private String phoneNumber;
    private String alternatePhoneNumber;

    // Timestamps
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    // Reference to roles table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;


    public User() {
        //default constructor
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
//        this.role = DEFAULT_ROLE;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // added hashing for the password.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Before Encoding "+password);
        System.out.println("After Encoding "+passwordEncoder.encode(password));
        System.out.println("After Encoding "+passwordEncoder.encode(password));
        this.password = passwordEncoder.encode(password);
    }
}
