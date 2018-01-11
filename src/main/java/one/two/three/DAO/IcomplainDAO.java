package one.two.three.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import one.two.three.entity.Complain;

public interface IcomplainDAO extends JpaRepository<Complain, Integer> {
	
	@Query("SELECT c FROM Complain c LEFT JOIN FETCH c.author WHERE c.date LIKE (:datePattern)")
	public List<Complain> findTodayComplains(@Param("datePattern") String datePattern);
	
	@Query("SELECT c FROM Complain c LEFT JOIN FETCH c.author")
	List<Complain> findAllWithAuthor();

}
