package es.GameSquare.GameSquareApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModDeveloperRepository extends JpaRepository<ModDeveloper, Long>{

}
