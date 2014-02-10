

	var RWindow = function(id, based) {
	
		var mouseDownX = 0;
		var mouseDownY = 0;
		var objectX = 0;
		var objectY = 0;
		var contentId = id + "contentId";
		var barId = id + "barId";
		var buttonCloseId = id + "buttonCloseId";
		var buttonMagnifyId = id + "buttonMagnifyId";
		var buttonReduceId = id + "buttonReduceId";
		var titleId = id + "titleId";
		var oldHtml = $("#" + id).html();
		
		
		
var div = "<div id=\""+id+"\" style=\"position:absolute; left:10px; top:109px; width:400px; height:300px;\"></div>";
var html = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">" + 
"  <tr>" + 
"    <td width=\"8\" height=\"35\"><img src=\""+based+"/window/4.png\" /></td>" + 
"    <td id=\""+barId+"\" style=\"background-image:url("+based+"/window/5.png); background-repeat:repeat-x\">" + 
"    <div style=\"padding-top:8px\">" + 
"        <div style=\"float:left;\"><img id=\"icon\" src=\""+based+"/window/title.gif\" /></div>" + 
"        <div style=\"padding-left:10px; float:left;\"><span id=\""+titleId+"\">標題</span></div>" + 
"        <div style=\"float:right; padding-left:10px; cursor:pointer\"><img id=\""+buttonCloseId+"\" src=\""+based+"/window/2.png\" /></div>" + 
"        <div style=\"float:right; padding-left:10px; cursor:pointer\"><img id=\""+buttonMagnifyId+"\" src=\""+based+"/window/1.png\" /></div>" + 
"        <div style=\"float:right; padding-left:10px; cursor:pointer\"><img id=\""+buttonReduceId+"\" src=\""+based+"/window/3.png\" /></div>" + 
"    </div>" + 
"    </td>" + 

"    <td width=\"13\"><img src=\""+based+"/window/6.png\" /></td>" + 
"  </tr>" + 
"  <tr>" + 
"    <td style=\"background-image:url("+based+"/window/7.png); background-repeat:repeat-y\">&nbsp;</td>" + 
"    <td valign=\"top\" bgcolor=\"#CED9E7\" style=\"border:1px solid #97a2b0\">" + 
"    <div style=\"width:100%; height:100%; overflow:auto;\" id=\""+contentId+"\">" + 
"    </div>" + 
"    </td>" + 
"    <td style=\"background-image:url("+based+"/window/11.png);background-repeat:repeat-y\">&nbsp;</td>" + 
"  </tr>" + 
"  <tr>" + 
"    <td valign=\"top\"><img src=\""+based+"/window/8.png\" /></td>" + 
"    <td style=\"background-image:url("+based+"/window/9.png); background-repeat:repeat-x\">&nbsp;</td>" + 
"    <td height=\"15\" valign=\"top\" id=\"borderBottomId\"><img src=\""+based+"/window/10.png\" /></td>" + 
"  </tr>" + 
"</table>";
		$("#" + id).replaceWith(div);
		$("#" + id).html(html);
	
		$("#" + titleId).html("test");
	
		$("#" + contentId).html(oldHtml);
		$("#" +contentId).css("background-color", "#FFF");
		
		$("#" + barId).css("cursor", "default");
		

		$("#" + barId).bind("mousedown", function(e) {		
			$("div").each(function() {
				if($(this).css("position") == "absolute") {
					$(this).css("z-index", 0);
				}
			});
			mouseDownX = e.pageX;
			mouseDownY = e.pageY;
			objectX = $("#" + id).css("left");
			objectY = $("#" + id).css("top");
			$("#" + id).css("z-index", 1);
			if(objectX.substring(objectX.length - 2, objectX.length) == "px") {
				objectX = new Number(objectX.substring(0, objectX.length - 2));
			}
			if(objectY.substring(objectY.length - 2, objectY.length) == "px") {
				objectY = new Number(objectY.substring(0, objectY.length - 2));
			}
			
			$(document).bind("mousemove",onMouseMove);
		});
		
		$("#" + barId).bind("mouseup", function(e) {
			$(document).unbind("mousemove", onMouseMove);
		});

		$("#" + buttonCloseId).bind("click", function(e) {
			$("#" + id).remove();
		});

		$("#" + buttonCloseId).bind("mouseover", function(e) {
			$(this).attr({src : based + "/window/14.png"});
		});
		$("#" + buttonCloseId).bind("mouseout", function(e) {
			$(this).attr({src : based + "/window/2.png"});
		});
		
		$("#" + buttonReduce).bind("mouseover", function(e) {
			$(this).attr({src : based + "/window/12.png"});
		});
		$("#" + buttonReduce).bind("mouseout", function(e) {
			$(this).attr({src : based + "/window/3.png"});
		});
		$("#" + buttonMagnify).bind("mouseover", function(e) {
			$(this).attr({src : based + "/window/13.png"});
		});
		$("#" + buttonMagnify).bind("mouseout", function(e) {
			$(this).attr({src : based + "/window/1.png"});
		});
		
		
		
		function onMouseMove(e) {
		
			var offsetX = e.pageX - mouseDownX;
			var offsetY = e.pageY - mouseDownY;
			//$("#" + contentId).html((offsetX + objectX) + "," + (offsetY + objectX));
			$("#" + id).css("left", (offsetX + objectX) + "px");
			$("#" + id).css("top", (offsetY + objectY) + "px");
		
		}
	}
	RWindow.prototype.initial = function(template) {
		
	}