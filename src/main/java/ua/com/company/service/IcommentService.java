package ua.com.company.service;

import java.util.List;

import ua.com.company.entity.Comment;

public interface IcommentService {

	public void saveComment(Comment comment);

	public void addCommentToProduct(int prodId, String comment, int user_id);

	public void deleteComment(int comment_id);

	public Comment oneCommentWithUser(int comment_id);

	public List<Comment> userComments(int userId);

}
