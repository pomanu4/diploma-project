package ua.com.company.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import ua.com.company.components.ActivUserOrderDetailChecker;
import ua.com.company.components.ProductListWrapper;
import ua.com.company.entity.Product;
import ua.com.company.entity.User;
import ua.com.company.service.IproductService;
import ua.com.company.validator.ProductValidator;

@SessionAttributes("prodListContainer")
@Controller
public class ProductObjectController {

	@Autowired
	private IproductService productService;
	@Autowired
	private ProductValidator productValidator;
	@Autowired
	ActivUserOrderDetailChecker checker;

	private int thisUserId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		return user.getId();
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String testMethod(Model model) {
		int id = 1;
		Product product = productService.productWithAllInfo(id);
		model.addAttribute("product", product);

		return "test";
	}

	@RequestMapping(value = "usr-changePrice", method = RequestMethod.GET)
	public String addPrice(@RequestParam("prodid") int product_id,
			@RequestParam("prodPrice") int newPrice, Model model) {
		productService.setNewPrice(product_id, newPrice);
		Product productANDcoments = productService.oneProdWithCommentandComUser(product_id);
		Product productANDpictures = productService.findByIdWithPicture(product_id);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "usr-addMoreProduct", method = RequestMethod.GET)
	public String addMoreProduct(@RequestParam("prodid") int product_id,
			@RequestParam("prodQantity") int howManyAdd, Model model) {
		productService.addProductQantity(product_id, howManyAdd);
		Product productANDcoments = productService.oneProdWithCommentandComUser(product_id);
		Product productANDpictures = productService.findByIdWithPicture(product_id);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "usr-addNewInfo", method = RequestMethod.GET)
	public String addNewInfo(@RequestParam("prodid") int product_id,
			@RequestParam("prodInfo") String newInfo, Model model) {
		productService.setNewInfoToProduct(product_id, newInfo);
		Product productANDcoments = productService.oneProdWithCommentandComUser(product_id);
		Product productANDpictures = productService.findByIdWithPicture(product_id);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "usr-changeName", method = RequestMethod.GET)
	public String setNewProductName(Model model, @RequestParam("prodid") int prodId,
			@RequestParam("prodName") String newName) {
		productService.setNewProductName(newName, prodId);
		Product productANDcoments = productService.oneProdWithCommentandComUser(prodId);
		Product productANDpictures = productService.findByIdWithPicture(prodId);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "usr-myProd-{id}", method = RequestMethod.GET)
	public String userProductByID(Model model, @PathVariable("id") int id) {
		Product productWithOwner = productService.productWithOwnerById(id);
		if (productWithOwner.getOwner().getId() != thisUserId()) {
			return "redirect:usr-userpage";
		}
		Product productANDcoments = productService.oneProdWithCommentandComUser(id);
		Product productANDpictures = productService.findByIdWithPicture(id);
		boolean val = productANDpictures.getPictures().isEmpty();
		model.addAttribute("isEmty", val);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@RequestMapping(value = "usr-product-{id}", method = RequestMethod.GET)
	public String prodByID(Model model, @PathVariable("id") int id) {
		Product product = productService.productWithAllInfo(id);
		Product productComment = productService.oneProdWithCommentandComUser(id);
		boolean val = product.getPictures().isEmpty();
		model.addAttribute("isEmty", val);
		model.addAttribute("prodPicture", product);
		model.addAttribute("prodComent", productComment);

		return "oneproduct";
	}

	@RequestMapping(value = "usr-showAllProducts", method = RequestMethod.GET)
	public String allProducts(Model model) {
		List<Product> products = productService.prodWithPicture();
		ProductListWrapper wrap = new ProductListWrapper();
		wrap.setProdlist(products);
		boolean anyActive = checker.checkActiveOrder(thisUserId());
		model.addAttribute("prodListContainer", wrap);
		model.addAttribute("active", anyActive);
		
		return "productpage";
	}

	@RequestMapping(value = "admin-adminUserProducts", method = RequestMethod.GET)
	public String adminUserProducts(Model model, @RequestParam("userId") int user_id) {
		List<Product> productList = productService.thisUsersProducts(user_id);
		model.addAttribute("prodList", productList);

		return "adminProduct";
	}

	@RequestMapping(value = "admin-adminProduct-{id}", method = RequestMethod.GET)
	public String adminUserProductDetail(@PathVariable("id") int prod_id, Model model) {
		Product productWithOwner = productService.productWithOwnerById(prod_id);
		Product productWithPictures = productService.findByIdWithPicture(prod_id);
		Product productWithComments = productService.oneProdWithCommentandComUser(prod_id);
		boolean val = productWithPictures.getPictures().isEmpty();
		model.addAttribute("isEmty", val);
		model.addAttribute("prodPictures", productWithPictures);
		model.addAttribute("prodOwner", productWithOwner);
		model.addAttribute("prodcomment", productWithComments);

		return "adminProductDetails";
	}

	@RequestMapping(value = "admin-commentPprod-{id}", method = RequestMethod.GET)
	public String comentedProduct(@PathVariable("id") int prod_id, Model model) {
		Product productWithOwner = productService.productWithOwnerById(prod_id);
		Product productWithPictures = productService.findByIdWithPicture(prod_id);
		Product productWithComments = productService.oneProdWithCommentandComUser(prod_id);
		boolean val = productWithPictures.getPictures().isEmpty();
		model.addAttribute("isEmty", val);
		model.addAttribute("prodPictures", productWithPictures);
		model.addAttribute("prodOwner", productWithOwner);
		model.addAttribute("prodcomment", productWithComments);

		return "adminProductDetails";
	}

	@RequestMapping(value = "usr-showMyProduct", method = RequestMethod.GET)
	public String usersProducts(Model model) {
		List<Product> usersProduct = productService.thisUsersProducts(thisUserId());
		model.addAttribute("userproducts", usersProduct);

		return "userProduct";
	}

	@RequestMapping(value = "usr-findByPattern", method = RequestMethod.GET)
	public String productsByPattern(Model model, @RequestParam("nameLike") String pattern) {
		List<Product> productList = productService.findByProductNamePattern(pattern);
		ProductListWrapper sortWrap = new ProductListWrapper();
		sortWrap.setProdlist(productList);
		model.addAttribute("prodListContainer", sortWrap);

		return "productpage";
	}

	@RequestMapping(value = "usr-sortByName", method = RequestMethod.GET)
	public String productsSortedByName(Model model, @SessionAttribute("prodListContainer") ProductListWrapper wrap) {
		List<Product> productList = productService.sortByName(wrap.getProdlist());
		ProductListWrapper sortWrap = new ProductListWrapper();
		sortWrap.setProdlist(productList);
		model.addAttribute("prodListContainer", sortWrap);

		return "productpage";
	}

	@RequestMapping(value = "usr-sortByPrice", method = RequestMethod.GET)
	public String productSortedByPrice(Model model, @SessionAttribute("prodListContainer") ProductListWrapper wrap) {
		List<Product> productList = productService.sortByPrice(wrap.getProdlist());
		ProductListWrapper sortWrap = new ProductListWrapper();
		sortWrap.setProdlist(productList);
		model.addAttribute("prodListContainer", sortWrap);

		return "productpage";
	}

	@RequestMapping(value = "usr-addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") @Valid Product prod, BindingResult result,
			@RequestParam("prodPicture") MultipartFile picture, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("text", "error");
			return "userpage";
		}
		int userID = thisUserId();
		productService.saveProductWithInfo(picture, prod, userID);
		model.addAttribute("text", "product added");
		
		return "userpage";
	}

	@RequestMapping(value = "usr-setpicture", method = RequestMethod.POST)
	public String addPicture(@RequestParam("picture") MultipartFile picture,
			@RequestParam("prodId") int id, Model model) {
		productService.setPictureToProduct(picture, id);
		Product productANDcoments = productService.oneProdWithCommentandComUser(id);
		Product productANDpictures = productService.findByIdWithPicture(id);
		model.addAttribute("productWithComents", productANDcoments);
		model.addAttribute("productWithPictures", productANDpictures);

		return "userProductDetails";
	}

	@InitBinder(value = "product")
	public void binderValidProduct(WebDataBinder binder) {
		binder.addValidators(productValidator);
	}

}
