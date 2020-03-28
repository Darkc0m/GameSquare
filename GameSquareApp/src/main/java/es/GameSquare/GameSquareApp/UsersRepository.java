package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>{
	User findByUserName(String username);
	
	List<User> findByUserNameNot(String username);
}
