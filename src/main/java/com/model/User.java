package com.model;
import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
	private Long id;
	private String username;
	private String password;
	private String passwordConfirm;
	private List<Task> tasks;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}
	
	public String getUsername() {
	    return username;
	}
	
	public void setUsername(String username) {
	    this.username = username;
	}
	
	public String getPassword() {
	    return password;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}
	
	@Transient
	public String getPasswordConfirm() {
	    return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
	    this.passwordConfirm = passwordConfirm;
	}
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "user", cascade = CascadeType.ALL)
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
