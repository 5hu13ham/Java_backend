package in.trendsnag.user_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.trendsnag.user_management.model.User;
import in.trendsnag.user_management.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		return ResponseEntity.ok(userService.createUser(user));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser (@PathVariable Long id,@RequestBody User user){
		return ResponseEntity.ok(userService.updateUser(id, user));
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> softDeleteUser(@PathVariable Long id){
		userService.softDeleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}
}
