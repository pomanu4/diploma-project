package one.two.three.service.servisImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IcommentDAO;
import one.two.three.DAO.IproductDAO;
import one.two.three.entity.Comment;
import one.two.three.entity.Product;
import one.two.three.entity.User;
import one.two.three.service.IcommentService;
import one.two.three.service.IuserService;

@Service
@Transactional
public class CommentService implements IcommentService {
	
	@Autowired
	private IcommentDAO commentDAO;
	@Autowired
	private IproductDAO prodDAO;
	@Autowired
	private IuserService userService;

	@Override
	public void saveComment(Comment comment) {
		commentDAO.save(comment);
	}
	
	@Override
	public Comment oneCommentWithUser(int comment_id) {
		Comment comment = commentDAO.oneCommentWithUser(comment_id);
		return comment;
	}

	@Override
	public void addCommentToProduct(int prodId, String comment, int userId) {
		User user = userService.oneUserWithComment(userId);		
		Product product = prodDAO.oneProductWithComment(prodId);
		Comment com = new Comment(comment);
		product.addComment(com);
		user.addMyComment(com);
		saveComment(com);
	}

	@Override
	public void deleteComment(int comment_id) {
		Comment commentANDproduct = commentDAO.oneCommentWithProduct(comment_id);
		commentANDproduct.getProductToComent().getComments().remove(commentANDproduct);
		commentANDproduct.setProductToComent(null);
		saveComment(commentANDproduct);
		Comment commentANDuser = commentDAO.oneCommentWithUser(comment_id);
		commentANDuser.getUser().getMyComment().remove(commentANDuser);
		commentANDuser.setUser(null);
		commentDAO.delete(commentANDuser);
	}

	@Override
	public List<Comment> userComments(int userId) {
		List<Comment> commentList = new ArrayList<>();
		commentList.addAll(commentDAO.thisUserComments(userId));
		return commentList;
	}
	
	
	

}
