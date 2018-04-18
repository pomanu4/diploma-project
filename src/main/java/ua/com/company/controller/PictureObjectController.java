package ua.com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.company.entity.Product;
import ua.com.company.service.IpictureService;
import ua.com.company.service.IproductService;

@Controller
public class PictureObjectController {

	@Autowired
	private IpictureService productPictureService;
	@Autowired
	private IproductService productService;

	@RequestMapping(value = "usr-removePicture", method = RequestMethod.GET)
	public String removePicture(@RequestParam("pictureId") int pic_id,
			@RequestParam("productId") int productId, Model model) {
		productPictureService.deleteById(pic_id);
		Product productANDcoments = productService.findByIdWithComment(productId);
		Product productANDpictures = productService.findByIdWithPicture(productId);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "admin-defaultPhoto", method = RequestMethod.GET)
	public String adminRemovePicture(@RequestParam("pictureId") int pic_id,
			@RequestParam("prodId") int prodId,	Model model) {
		productPictureService.setDefault(pic_id);
		Product productWithOwner = productService.productWithOwnerById(prodId);
		Product productWithPictures = productService.findByIdWithPicture(prodId);
		Product productWithComments = productService.oneProdWithCommentandComUser(prodId);
		model.addAttribute("prodPictures", productWithPictures);
		model.addAttribute("prodOwner", productWithOwner);
		model.addAttribute("prodcomment", productWithComments);

		return "adminProductDetails";
	}

}
