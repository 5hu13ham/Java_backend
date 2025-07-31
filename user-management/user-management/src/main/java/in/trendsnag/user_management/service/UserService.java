package in.trendsnag.user_management.service;

import in.trendsnag.user_management.model.*;
import in.trendsnag.user_management.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

interface UserServiceInterface {
	
	User createUser(User user);
	User updateUser(Long id, User user);
	void deleteUser(Long id);
	
	List<User> getAllUsers();
	Optional<User> findUserById(Long id);
	Optional<User> findUserByUsername(String username);
	Optional<User> findUserByEmail(String email);
	
	List<User> findByRole(Role role);
	List<User> findByActive(boolean active);
	List<User> findByFirstNameContaining(String keyword);
	
	void softDeleteUser (Long id);
	
	boolean isUsernameExists(String username);
	
	long countByRole(String role);
	long countByActive(boolean active);
	

}

@Service
@Transactional
public class UserService implements UserServiceInterface{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
		    throw new IllegalArgumentException("Username taken, try another one");
		}
		
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exists");
		}
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, User updatedUser) {
	    User existingUser = userRepository.findById(id)
	        .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

	    // Update editable fields only
	    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
	        existingUser.setPassword(updatedUser.getPassword());
	    }
	    
	    if(updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
	    	existingUser.setUsername(updatedUser.getUsername());
	    }
	    
	    if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isBlank()) {
	        existingUser.setFirstName(updatedUser.getFirstName());
	    }

	    if (updatedUser.getLastName() != null && !updatedUser.getLastName().isBlank()) {
	        existingUser.setLastName(updatedUser.getLastName());
	    }

	    if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
	        Optional<User> userWithEmail = userRepository.findByEmail(updatedUser.getEmail());
	        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
	            throw new IllegalArgumentException("Email already in use by another user.");
	        }
	        existingUser.setEmail(updatedUser.getEmail());
	    }

	    if (updatedUser.getPhone() != null && !updatedUser.getPhone().isBlank()) {
	        existingUser.setPhone(updatedUser.getPhone());
	    }

	    if (updatedUser.getAge() != null && updatedUser.getAge() > 0) {
	        existingUser.setAge(updatedUser.getAge());
	    }
	    

	    return userRepository.save(existingUser);
	}



	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		// TODO Auto-generated method stub
		
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		// TODO Auto-generated method stub
		
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findByRole(Role role) {
		// TODO Auto-generated method stub
		
		return userRepository.findByRole(role);
	}

	@Override
	public List<User> findByActive(boolean active) {
		// TODO Auto-generated method stub
		return userRepository.findByActive(active);
	}

	@Override
	public List<User> findByFirstNameContaining(String keyword) {
		// TODO Auto-generated method stub

	    return userRepository.findByFirstNameContaining(keyword);
	}

	@Override
	public void softDeleteUser(Long id) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found with id: "+id));
		
		user.setActive(false);
		userRepository.save(user);
		
	}

	@Override
	public boolean isUsernameExists(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username).isPresent();
	}

	@Override
	public long countByRole(String role) {
		// TODO Auto-generated method stub
		try {
	        Role roleEnum = Role.valueOf(role.toUpperCase());
	        return userRepository.countByRole(roleEnum);
	    } catch (IllegalArgumentException ex) {
	        throw new IllegalArgumentException("Invalid role: " + role);
	    }
	}

	@Override
	public long countByActive(boolean active) {
		// TODO Auto-generated method stub
		return userRepository.countByActive(active);
	}
	
}
