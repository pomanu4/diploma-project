package one.two.three.service;

import one.two.three.entity.Comment;

public interface IcommentService {
	
	public void saveComment(Comment comment);
	
	public void addCommentToProduct(int prodId, String comment);

}
