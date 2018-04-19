package ua.com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.company.components.ProductOwnerChecker;
import ua.com.company.entity.Comment;
import ua.com.company.entity.Product;
import ua.com.company.entity.User;
import ua.com.company.service.IcommentService;
import ua.com.company.service.IproductService;

@Controller
public class CommentObjectController {

	@Autowired
	private IcommentService commentService;
	@Autowired
	private ProductOwnerChecker prodOwnerChecker;
	@Autowired
	private IproductService productService;

	private int thisUserId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getId();
	}

	@RequestMapping(value = "admin-deleteComment", method = RequestMethod.GET)
	public String deleteComment(@RequestParam("commentId") int comment_id, @RequestParam("productId") int prod_id,
			Model model) {
		commentService.deleteComment(comment_id);
		Product productWithOwner = productService.productWithOwnerById(prod_id);
		Product productWithPictures = productService.findByIdWithPicture(prod_id);
		Product productWithComments = productService.oneProdWithCommentandComUser(prod_id);
		model.addAttribute("prodPictures", productWithPictures);
		model.addAttribute("prodOwner", productWithOwner);
		model.addAttribute("prodcomment", productWithComments);

		return "adminProductDetails";
	}

	@RequestMapping(value = "admin-adminUserComments", method = RequestMethod.GET)
	public String adminUserComents(Model model, @RequestParam("userId") int userId) {
		List<Comment> commentList = commentService.userComments(userId);
		model.addAttribute("comments", commentList);

		return "adminUserComments";
	}

	@RequestMapping(value = "usr-addComment", method = RequestMethod.POST)
	public String addComent(@RequestParam("productId") int prod_id, @RequestParam("commentText") String commentText,
			Model model) {
		if (prodOwnerChecker.checkOwner(thisUserId(), prod_id)) {
			return "comentDenied";
		}
		commentService.addCommentToProduct(prod_id, commentText, thisUserId());
		Product productPict = productService.productWithAllInfo(prod_id);
		Product productComm = productService.oneProdWithCommentandComUser(prod_id);
		model.addAttribute("prodPicture", productPict);
		model.addAttribute("prodComent", productComm);

		return "oneproduct";
	}

}
