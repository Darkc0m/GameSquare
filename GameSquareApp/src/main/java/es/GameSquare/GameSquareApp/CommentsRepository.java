package es.GameSquare.GameSquareApp;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames="comentarios")
public interface CommentsRepository extends JpaRepository<Comment, Long>{
	
	@CacheEvict(allEntries=true)
	Comment save(Comment comment);
	
	@Cacheable
	Page<Comment> findByOwnerOrderByPubDateDesc(String owner, Pageable page);
	
	
}
