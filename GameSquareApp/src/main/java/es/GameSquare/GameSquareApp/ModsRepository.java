package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModsRepository extends JpaRepository<Mod, Long>{

	List<Mod> findAll(Sort sort);
	
	List<Mod> findFirst10ByOrderByPubDateDesc();
}
