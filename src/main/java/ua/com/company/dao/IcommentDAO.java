package ua.com.company.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.company.entity.Comment;

public interface IcommentDAO extends JpaRepository<Comment, Integer> {

	@Query("SELECT c FROM Comment c LEFT JOIN FETCH c.productToComent WHERE c.id=(:commentId)")
	public Comment oneCommentWithProduct(@Param("commentId") Integer commentId);

	@Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user WHERE c.id=(:commentId)")
	public Comment oneCommentWithUser(@Param("commentId") Integer commentId);

	@Query("SELECT c FROM Comment c LEFT JOIN FETCH c.productToComent WHERE user_id=(:user_id)")
	public List<Comment> thisUserComments(@Param("user_id") Integer userId);

}
