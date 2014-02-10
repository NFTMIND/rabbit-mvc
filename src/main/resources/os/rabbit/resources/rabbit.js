	function toTextBox(element, func) {
		var textBoxElement = document.createElement("input");
		textBoxElement.type = "text";
		$(element).replaceWith(textBoxElement);
		textBoxElement.value = $(element).html();
		textBoxElement.focus();
		$(textBoxElement).blur(func);
		
		
	}

	var Rabbit = function() {
	}
	Rabbit.getLoadingPanel = function() {
	
		if(Rabbit.divObject == null) {
			Rabbit.divObject = document.createElement("div");
			Rabbit.divObject.style.position = "absolute";
	
			Rabbit.divObject.style.background = "#000000";
			Rabbit.divObject.style.filter = "alpha(opacity=50)";
			Rabbit.divObject.style.opacity = "0.5";
			Rabbit.divLoading = document.createElement("div");
			Rabbit.divLoading.style.position = "absolute";
			Rabbit.divLoading.style.border = "1px solid #333333";
			Rabbit.divLoading.style.background = "#FFFFFF";
			Rabbit.divLoading.innerHTML = "<img src=\"rbt/loading.gif\" />";
			Rabbit.divObject.divTag = Rabbit.divLoading;
		}
	
		return Rabbit.divObject;
	
	}
	
	
	Rabbit.invoke = function(url, triggerId, data, loadingScreen) {
		if(loadingScreen) {
			Rabbit.showLoadingScreen();
		}
		
//		var ndata = new Object();
//		for (var name in data){
//			if($.isArray(data[name])) {
//				
//				for(var i = 0; i < data[name].length; i++) {
//					ndata[name + "[" + i + "]"] = data[name][i];
//				}
//			} else {
//				ndata[name] = data[name];
//			}
//		}
		
		$.post(url + "?rbtType=AJAX_INVOKE&triggerId=" + triggerId, data, function(data,textStatus) {
			if(loadingScreen) {
				
				Rabbit.hideLoadingScreen();
			}
			
			
			$(data).find("html").each(function(i) {
				var id = $(this).attr("id");
				$("#" + id).replaceWith($(this).text());
			})
			$(data).find("script").each(function(i) {

				eval($(this).text());
			})
			$(data).find("error").each(function(i) {

				alert($(this).text());
			})
		
		}, "xml");
	}
	Rabbit.showLoadingScreen = function() {
		var divObject = Rabbit.getLoadingPanel();
		if(!divObject)return;
		var size = this.clientSize();
		divObject.style.left = 0;
		divObject.style.top = 0;
		divObject.style.width = size.width + "px";
		divObject.style.height = size.height + "px";

		
		divObject.divTag.style.left = size.width / 2 + "px";
		divObject.divTag.style.top = size.height / 2 + "px";
		
		
		document.body.appendChild(divObject);
		document.body.appendChild(divObject.divTag);
		divObject.style.display = "block";
		divObject.divTag.style.display = "block";
	}
	Rabbit.hideLoadingScreen = function() {
		var divObject = Rabbit.getLoadingPanel();
		divObject.style.display = "none";
		divObject.divTag.style.display = "none";
	}
	
	Rabbit.clientSize = function() {
		var size = {
			width : 0,
			height : 0
		};

		if(typeof( window.innerWidth ) == 'number' ) {
			//Not IE
			size.width = window.innerWidth;
			size.height = window.innerHeight;
		} else if(document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			//IE 6up
			size.width = document.documentElement.clientWidth;
			size.height = document.documentElement.clientHeight;
		} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			//IE 4,5
			size.width = document.body.clientWidth;
			size.height = document.body.clientHeight;
		} else {
			size.width = document.getElementsByTagName('body')[0].clientWidth,
			size.height = document.getElementsByTagName('body')[0].clientHeight
		}
		return size;
	};
	
	$(function(){});

	
	(function($) {
		$.fn.extend({
			toEditableLabel : function() {
				var refer = this;
				this.dblclick(function() {
					var text = $(this);
					var editor = $("<input type=\"text\" />");
					editor.val($(this).text());
					editor.blur(function() {

						try{
							text.css("display", "block");
							text.text($(this).val());
							eval(text.attr("onupdate"));
							$(this).remove();
						} catch(e) {
							alert(e);
						}
						
					});
					
					$(this).after(editor);
					editor.focus();
					
					$(this).css("display", "none");
					
				});
			}	 
		});
		

		$.fn.extend({
			fieldValue : function() {
				
				var returnValue = null;
				var instance = this;
				var tagName = instance.prop("tagName");
		
				if(tagName == "INPUT") {
		
					if(instance.attr("type") == "text") {
						returnValue = instance.val();
						
					} else if(instance.attr("type") == "radio") {
						var radio = $("input[name="+this.prop("name")+"]:checked");
						if(radio.size() > 0) {
							returnValue = radio.val();
						}
					} else if(instance.attr("type") == "checkbox") {
						var checkbox = this;
						//if(instance.attr("forceUpdate") == null ||  instance.attr("forceUpdate") != "true") {
						//	checkbox = $("input[name="+this.attr("name")+"]:checked");
						//}
			
						if(checkbox.length > 0) {
							var values = new Array();
							for(var i = 0; i < checkbox.length; i++) {
								//alert($(checkbox.get(i)).prop("checked") + $(checkbox.get(i)).val());
								if($(checkbox.get(i)).prop("checked")) {
									values.push($(checkbox.get(i)).val());
								}
							}
							//alert(values);
							if(values.length == 1) {
								return values[0];
							} else if(values.length > 1) {
								return values;
							}
						
						}
						return null;
					} else {
						returnValue = instance.val();
					}
				} else if(tagName == "TEXTAREA") {
					returnValue = instance.val();
				
				} else if(tagName == "SELECT") {
					return instance.val();
					
				} else {
					return instance.html();
				}
				return returnValue;

			}
		});
	})(jQuery);
	
	