package in.trendsnag.user_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private Integer phone;
	
	@Column
	private Integer age;
	
	
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
		
	public String getUserName() {
		return username;
		}
			
	public void setUserName(String username) {
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
	
	public Integer getPhone() {
		return phone;
	}
	
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}	

}
