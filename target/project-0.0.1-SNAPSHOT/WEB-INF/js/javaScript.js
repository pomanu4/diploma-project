function addProductButtonClick(){
	document.getElementById("addProduct").setAttribute("class", "addProductForm");
	document.getElementById("findProduct").setAttribute("class", "findProductByPatternInvisible");
	document.getElementById("complain").setAttribute("class", "addcomplainFormInvisible");
}
function searchButtonClick(){
	document.getElementById("addProduct").setAttribute("class", "addProductForminvisible");
	document.getElementById("findProduct").setAttribute("class", "findProductByPattern");
	document.getElementById("complain").setAttribute("class", "addcomplainFormInvisible");
}
function complainButtonClick(){
	document.getElementById("addProduct").setAttribute("class", "addProductForminvisible");
	document.getElementById("findProduct").setAttribute("class", "findProductByPatternInvisible");
	document.getElementById("complain").setAttribute("class", "addcomplainForm");
}
function showCommentForm(){
	let element = document.getElementById("comentForm");
	element.setAttribute("class", " vivsibility");
}
function hideCommentForm(){
	let element = document.getElementById("comentForm");
	element.setAttribute("class", "comentFormPosition");
}
function showNewContactForm() {
	document.getElementById("newContactInfo").setAttribute("class", "formBodyVisivle");
}
function hideNewContactForm() {
	document.getElementById("newContactInfo").setAttribute("class", "formBodyInvisible");
}
function showNewUserProdInfotForm() {
	document.getElementById("userNewProdInfo").setAttribute("class", "userNewProductInfo");
}
function hideNewUserProdInfotForm() {
	document.getElementById("userNewProdInfo").setAttribute("class", "userNewProductInfoInvisible");
}

$("#lang").click(function(cl) {
	var pos = $("#lang").position();
	var value = pos.left + 320;
	console.log(pos.left);
	$("#text").removeClass("multiLangInvisible");
	$("#text").addClass("multiLangVisible");
	$("#text").css('left', value);
});
carosele=( function(){
	let container = document.querySelector(".mainImg");
	let left = container.querySelector(".left");
	let right = container.querySelector(".right");
	let imgArray = container.querySelectorAll(".imgContent img");
	let imgContainer = container.querySelector(".imgContent");
	let count = 0;
	let imgNumber = imgArray.length;
	console.log(imgNumber);
	
	function move(num){
		if(num === 1){
			count = count + num;
			if(count === imgNumber){
				count = 0;
			}
		}
		if(num === -1){
			count = count + num;
			if(count < 0 ){
				count = imgNumber-1;
			}
		}
		
		let val = count * (-300);
		let pixel = val + "px";
		imgContainer.style.top = pixel;
	}
	
	right.addEventListener('click', function(ivent){
		move(1);
	});
	left.addEventListener('click', function(ivent){
		move(-1);
	});
})();













