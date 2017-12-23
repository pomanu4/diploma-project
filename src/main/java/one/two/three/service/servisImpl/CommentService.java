package one.two.three.service.servisImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IcommentDAO;
import one.two.three.DAO.IproductDAO;
import one.two.three.entity.Comment;
import one.two.three.entity.Product;
import one.two.three.service.IcommentService;

@Service
@Transactional
public class CommentService implements IcommentService {
	
	@Autowired
	IcommentDAO commentDAO;
	@Autowired
	IproductDAO prodDAO;

	@Override
	public void saveComment(Comment comment) {
		commentDAO.save(comment);
	}

	@Override
	public void addCommentToProduct(int prodId, String comment) {
		Product product = prodDAO.oneProductWithComment(prodId);
		Comment com = new Comment(comment);
		product.addComment(com);
		commentDAO.save(com);
	}
	
	

}
