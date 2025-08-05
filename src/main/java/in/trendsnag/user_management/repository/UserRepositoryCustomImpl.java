package in.trendsnag.user_management.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import in.trendsnag.user_management.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<User> FilterSearchSortUsers(
			String keyword,
	        String role,
	        Boolean active,
	        Boolean deleted,
	        String ageGroup,
	        int page,
	        int size,
	        String sortField,
	        String sortDirection
	        ){
		
		String baseQuery = "SELECT * FROM users WHERE 1=1";
		String countQuery = "SELECT COUNT(*) FROM users WHERE 1=1";

		StringBuilder filterBuilder = new StringBuilder();
		
		if (keyword != null && !keyword.isEmpty()) {
		    filterBuilder.append(" AND (LOWER(username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		                 .append(" OR LOWER(email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		                 .append(" OR LOWER(first_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		                 .append(" OR LOWER(last_name) LIKE LOWER(CONCAT('%', :keyword, '%')))");
		}

		if (role != null) filterBuilder.append(" AND role = :role");
		
		if (active != null) filterBuilder.append(" AND active = :active");
		
		if (deleted != null) filterBuilder.append(" AND is_deleted = :deleted");

		if (ageGroup != null) {
		    switch (ageGroup.toLowerCase()) {
			    case "toddlers":
			    	filterBuilder.append(" AND age < 13");
			    	break;		    	
		        case "teenagers":
		            filterBuilder.append(" AND age BETWEEN 13 AND 19");
		            break;
		        case "adults":
		            filterBuilder.append(" AND age BETWEEN 20 AND 59");
		            break;
		        case "elderly":
		            filterBuilder.append(" AND age >= 60");
		            break;
		    }
		}
		
		String order = "";
		List<String> allowedSortFields = List.of("username", "email", "first_name", "last_name", "age", "role");
		if (allowedSortFields.contains(sortField)) {
		    order = " ORDER BY " + sortField + ("desc".equalsIgnoreCase(sortDirection) ? " DESC" : " ASC");
		}
		
		String finalQuery = baseQuery + filterBuilder + order + " LIMIT :limit OFFSET :offset";
		String finalCountQuery = countQuery + filterBuilder;
		
		 Query nativeQuery = entityManager.createNativeQuery(finalQuery, User.class);
	        Query countNativeQuery = entityManager.createNativeQuery(finalCountQuery);

	        if (keyword != null && !keyword.isEmpty()) {
	            nativeQuery.setParameter("keyword", keyword);
	            countNativeQuery.setParameter("keyword", keyword);
	        }
	        if (role != null) {
	            nativeQuery.setParameter("role", role);
	            countNativeQuery.setParameter("role", role);
	        }
	        if (active != null) {
	            nativeQuery.setParameter("active", active);
	            countNativeQuery.setParameter("active", active);
	        }
	        if (deleted != null) {
	            nativeQuery.setParameter("deleted", deleted);
	            countNativeQuery.setParameter("deleted", deleted);
	        }

	        // Pagination
	        nativeQuery.setParameter("limit", size);
	        nativeQuery.setParameter("offset", page * size);

	        @SuppressWarnings("unchecked")
	        List<User> results = nativeQuery.getResultList();
	        Number count = (Number) countNativeQuery.getSingleResult();

	        return new PageImpl<>(results, PageRequest.of(page, size), count.longValue());
	        
	}
}