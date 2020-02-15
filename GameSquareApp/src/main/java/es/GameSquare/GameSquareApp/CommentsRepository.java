package es.GameSquare.GameSquareApp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, Long>{
	
	Page<Comment> findBySoftwareNameOrderByPubDateDesc(String softwareName, Pageable page);
	
}
