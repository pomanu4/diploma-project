package one.two.three.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import one.two.three.entity.Comment;

public interface IcommentDAO extends JpaRepository<Comment, Integer> {

}
