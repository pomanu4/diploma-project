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

/* show multilanguage option */
$("#lang").click(function(cl) {
	var pos = $("#lang").position();
	var value = pos.left + 320;
	console.log(pos.left);
	$("#text").removeClass("multiLangInvisible");
	$("#text").addClass("multiLangVisible");
	$("#text").css('left', value);
});

/* caroselle */
let imgArray = $(".imgContent img");
let count = 0;
let imgNumber = imgArray.length;

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
	$(".imgContent").css({"top" : pixel});                     
}

$(".right").on('click', function(){
	move(1);
});
$(".left").on('click', function(){
	move(-1);
});

/* serching filter */
$("#search").on("input", function(){
	var text = $(this).val().toLowerCase();
	var regex = new RegExp("" + text + "" + "i");
	var aElements = $(".tableDiv a");
	
	for(i=0; i<aElements.length; i++){
		var row = $(aElements[i]).parents("tr");
		var innerText = $(aElements[i]).text();
		$(row).show();
		if(innerText.toLowerCase().indexOf(text)== -1){
			$(row).hide();
		}
		$(aElements[i]).html($(aElements[i]).text().replace(text, "<b>"+text+"</b>"));
	}
} );











