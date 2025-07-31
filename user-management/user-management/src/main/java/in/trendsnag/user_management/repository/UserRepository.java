package in.trendsnag.user_management.repository;

import in.trendsnag.user_management.model.Role;
import in.trendsnag.user_management.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByRole(Role role);
	List<User> findByActive(boolean active);
	List<User> findByFirstNameContaining(String keyword);
	
	long countByRole(Role role);
	long countByActive(boolean active);
	
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	
	
}
