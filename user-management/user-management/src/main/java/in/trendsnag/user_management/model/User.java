package in.trendsnag.user_management.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = true, unique = true)
	private String phone;
	
	@Column
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private Role role;
	
	@Column(nullable = false)
	private boolean active;
	
	@Column(nullable = false)
	private String password;
	
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
		
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(String role) {
	    try {
	        this.role = Role.valueOf(role.toUpperCase());
	    } catch (IllegalArgumentException ex) {
	        throw new IllegalArgumentException("Invalid role: " + role);
	    }
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
		
	public void setEmail(String email) {
		this.email = email;
		}
		
	public String getUsername() {
		return username;
		}
			
	public void setUsername(String username) {
		this.username = username;
		}
			
	public String getFirstName() {
		return firstName;
		}
				
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
				
	public String getLastName() {
		return lastName;
	}
					
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}	

}
