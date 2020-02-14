package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideogamesRepository extends JpaRepository<Videogame, Long>{

	List<Videogame> findAll(Sort sort);
	
	List<Videogame> findFirst10ByOrderByPubDateDesc();
	
	Page<Videogame> findByNameContainingIgnoreCaseOrderByPubDateDesc(String name, Pageable page);
	Page<Videogame> findAllByOrderByPubDateDesc(Pageable page);
}
