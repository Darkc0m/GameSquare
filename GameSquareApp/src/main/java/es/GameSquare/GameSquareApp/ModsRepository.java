package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModsRepository extends JpaRepository<Mod, Long>{

	List<Mod> findAll(Sort sort);
	
	List<Mod> findFirst10ByOrderByPubDateDesc();
	
	Page<Mod> findByVideogameNameOrderByPubDateDesc(String name, Pageable page);
	
	Page<Mod> findByNameContainingIgnoreCaseOrderByPubDateDesc(String name, Pageable page);
	
	Page<Mod> findAllByOrderByPubDateDesc(Pageable page);
	
	List<Mod> findByDeveloperOrderByPubDateDesc(String developer);
	List<Mod> findByDeveloper(String developer);
}
