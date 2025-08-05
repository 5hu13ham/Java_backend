package in.trendsnag.user_management.repository;

import org.springframework.data.domain.Page;

import in.trendsnag.user_management.model.User;


public interface UserRepositoryCustom{
	Page<User> FilterSearchSortUsers(
			String keyword,
			String role,
			Boolean active,
			Boolean deleted,
			String ageGroup,
			int page,
			int size,
			String sortByfield,
			String sortDirection);
}
