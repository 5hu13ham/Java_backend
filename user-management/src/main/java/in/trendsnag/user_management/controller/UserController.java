package in.trendsnag.user_management.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import in.trendsnag.user_management.dto.UserRequestDTO;
import in.trendsnag.user_management.dto.UserResponseDTO;
import in.trendsnag.user_management.mapper.UserDTOEntityMapper;
import in.trendsnag.user_management.model.User;
import in.trendsnag.user_management.payload.ApiResponse;
import in.trendsnag.user_management.service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO requestDTO){
		User user = UserDTOEntityMapper.toEntity(requestDTO);
		User created = userService.createUser(user);
		UserResponseDTO responseDTO = UserDTOEntityMapper.toResponseDTO(created);
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>("User Creation Successful", responseDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@Valid @PathVariable Long id,@RequestBody UserRequestDTO requestDTO){
		User user = UserDTOEntityMapper.toEntity(requestDTO);
		User updated = userService.updateUser(id, user);
		UserResponseDTO responseDTO = UserDTOEntityMapper.toResponseDTO(updated);
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>("User updated to new values", responseDTO);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id,asc") String[] sort) {

	    Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
	    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

	    Page<User> users = userService.getAllUsers(pageable); // Make sure this returns Page<User>

	    List<UserResponseDTO> responseDTO = users.getContent().stream()
	            .map(user -> UserDTOEntityMapper.toResponseDTO(user))
	            .toList();

	    ApiResponse<List<UserResponseDTO>> apiResponse = new ApiResponse<>("Data of all users", responseDTO);
	    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/filter-search")
	public ResponseEntity<ApiResponse<Map<String, Object>>> filterAndSearchUsers(
	    @RequestParam(required = false) String keyword,
	    @RequestParam(required = false) String role,
	    @RequestParam(required = false) Boolean active,
	    @RequestParam(required = false) Boolean deleted,
	    @RequestParam(required = false) String ageGroup,
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "10") int size,
	    @RequestParam(defaultValue = "id") String sortBy,
	    @RequestParam(defaultValue = "asc") String sortDir
	) {
	    Page<User> userPage = userService.FilterSearchSortUsers(
	        keyword, role, active, deleted, ageGroup, page, size, sortBy, sortDir
	    );

	    List<UserResponseDTO> userDTOs = userPage.getContent()
	        .stream()
	        .map(user -> UserDTOEntityMapper.toResponseDTO(user))
	        .toList();

	    Map<String, Object> data = new HashMap<>();
	    data.put("users", userDTOs);
	    data.put("currentPage", userPage.getNumber());
	    data.put("totalItems", userPage.getTotalElements());
	    data.put("totalPages", userPage.getTotalPages());
	    data.put("pageSize", userPage.getSize());
	    data.put("hasNext", userPage.hasNext());
	    data.put("hasPrevious", userPage.hasPrevious());

	    ApiResponse<Map<String, Object>> response = new ApiResponse<>(
	        "Filtered user list fetched successfully",
	        data
	    );

	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
		User user = userService.findUserById(id);
		UserResponseDTO responseDTO = UserDTOEntityMapper.toResponseDTO(user);
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>("Here's the information you require", responseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> softDeleteUser(@PathVariable Long id){
		userService.softDeleteUser(id);
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>("User deleted successfully", null);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
}
