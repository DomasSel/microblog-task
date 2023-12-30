package com.task.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "USERS")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @Column
    @NotBlank(message = "Email is mandatory")
    private String email;
    
    @Column
    @NotBlank(message = "Password is mandatory")
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    
    @JsonIgnore
    @OneToMany(mappedBy="author")
    private Set<Blog> blogs;
    
    public User() {}
    
    public User(String username, String email, String password) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    }
	
	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Set<Blog> getBlogs() {
        return blogs;
    }
    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }
	
    // standard constructors / setters / getters / toString
}