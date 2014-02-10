var divObject = document.createElement("div");
divObject.style.position = "absolute";
divObject.style.background = "#000000";
divObject.style.filter = "alpha(opacity=50)";
divObject.style.opacity = "0.5";
var divObjectTop = 0;
$(function() {
	$(window).scroll(function() {
	var top = $(window).scrollTop();
	var left= $(window).scrollLeft();  
	divObject.style.left = left + "px";
	divObject.style.top = top + "px";
	divObjectTop = top;
	});
});

function showLoading() {
	if (!divObject)
		return;
	var size = clientSize();
	divObject.style.left = 0;
	divObject.style.top = divObjectTop;
	divObject.style.width = size.width + "px";
	divObject.style.height = size.height + "px";
	document.body.appendChild(divObject);
	divObject.display = "block";
}
function hideLoading() {
	document.body.removeChild(divObject);
	//divObject.display = "none";
}

function clientSize() {
	var size = {
		width : 0,
		height : 0
	};

	if (typeof (window.innerWidth) == 'number') {
		//Not IE
		size.width = window.innerWidth;
		size.height = window.innerHeight;
	} else if (document.documentElement
			&& (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		//IE 6up
		size.width = document.documentElement.clientWidth;
		size.height = document.documentElement.clientHeight;
	} else if (document.body
			&& (document.body.clientWidth || document.body.clientHeight)) {
		//IE 4,5
		size.width = document.body.clientWidth;
		size.height = document.body.clientHeight;
	} else {
				size.width = document.getElementsByTagName('body')[0].clientWidth,
				size.height = document.getElementsByTagName('body')[0].clientHeight
	}
	return size;
};
var prox;
var proy;
var proxc;
var proyc;
var sa_w;
var sa_h;
function showAnimation(id) {/*--打開--*/
	clearInterval(prox);
	clearInterval(proy);
	clearInterval(proxc);
	clearInterval(proyc);
	var o = document.getElementById(id);
	sa_w = parseInt(o.style.width);
	sa_h = parseInt(o.style.height);

	o.style.display = "block";
	o.style.width = "1px";
	o.style.height = "1px";
	prox = setInterval(function() {
		openx(o, sa_w, sa_h)
	}, 20);
}
function openx(o, w, h) {/*--打開x--*/

	var cx = parseInt(o.style.width);
	if (cx < w) {
		o.style.width = (cx + Math.ceil((w - cx) / 5)) + "px";
	} else {
		clearInterval(prox);
		proy = setInterval(function() {
			openy(o, h)
		}, 20);
	}
}
function openy(o, y) {/*--打開y--*/
	var cy = parseInt(o.style.height);
	if (cy < y) {
		o.style.height = (cy + Math.ceil((y - cy) / 5)) + "px";
	} else {
		clearInterval(proy);
	}
}
function hideAnimation(id) {/*--關閉--*/
	clearInterval(prox);
	clearInterval(proy);
	clearInterval(proxc);
	clearInterval(proyc);
	var o = document.getElementById(id);

	if (o.style.display == "block") {
		proyc = setInterval(function() {
			closey(o)
		}, 20);

	}
}
function closey(o) {/*--打開y--*/
	var cy = parseInt(o.style.height);

	if (cy > 0) {
		o.style.height = (cy - Math.ceil(cy / 5)) + "px";
	} else {
		clearInterval(proyc);
		proxc = setInterval(function() {
			closex(o)
		}, 20);
	}
}
function closex(o) {/*--打開x--*/
	var cx = parseInt(o.style.width);
	if (cx > 0) {
		o.style.width = (cx - Math.ceil(cx / 5)) + "px";
	} else {
		clearInterval(proxc);
		o.style.display = "none";
		o.style.width = sa_w;
		o.style.height = sa_h;
		hideLoading();
	}
}
//function divAloneCoordinate(name){
//	var w = document.body.scrollWidth ;
//	var h = document.body.scrollHeight ;
//	var d = $("#"+name);
//	d.css("left",  ( w - d.innerWidth() ) / 2);
//	d.css("top", ( h - d.height() ) / 2);
//}