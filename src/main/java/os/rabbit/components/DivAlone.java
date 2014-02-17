package os.rabbit.components;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import os.rabbit.IRender;
import os.rabbit.parser.Tag;

/**
 * @author homegowhat
 *
 */
public class DivAlone extends Component{

	public DivAlone(Tag tag) {
		super(tag);
	}
	
	
	

	@Override
	protected void afterBuild() {
		super.afterBuild();
	//	this.setTagAttributeModifier("style", true);
//		this.setTagAttributeModifier("class", true);
		getPage().addScriptImport("loading_div1", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRequest().getContextPath() + "/rbt/alone_div1.js");
			}
		});
		
	}
	
	@Override
	public void renderComponent(PrintWriter writer) {
		super.renderComponent(writer);
		
		String visible = (String)getAttribute("visible");
		String moveBarId = (String)this.getTag().getAttribute("rabbit:barId");
		
		StringBuffer str = new StringBuffer();
		str.append("<script>");
		str.append("var lc_x1,lc_y1; ");
		str.append("$(document).ready(function(){ ");
		str.append("$(\"#"+this.getId()+"\").addClass(\"moveDivX\");");
		if(visible != null){
			str.append("showLoading();");
			if(this.getTag().getAttribute("rabbit:animation")!=null){
				str.append("showAnimation(\"");
				str.append(this.getId());
				str.append("\");");
			}else{
				str.append("$(\"#");
				str.append(this.getId());
				str.append("\").show();");
			}
		}
		if(moveBarId != null){
		str.append("$(\"#"+moveBarId+"\").addClass(\"headDiv\");");
		str.append("$(\".headDiv\").mousedown( ");
		str.append(" function(event){ ");
		str.append("var offset=$(\"#"+this.getId()+"\").offset(); ");
		str.append("lc_x1=event.clientX-offset.left; ");
		str.append("lc_y1=event.clientY-offset.top; ");
		str.append("var witchButton=false; ");
		str.append("if(document.all&&event.button==1){witchButton=true;} ");
		str.append("else{if( event.button==0)witchButton=true;} ");
		str.append(" if(witchButton)");
		str.append("{ ");
		str.append(" $(document).mousemove(function(event){ ");
		str.append("$(\".moveDiv\"). css(\"left\",(event.clientX-lc_x1)+\"px\"); ");
		str.append(" $(\".moveDiv\").css(\"top\",(event.clientY-lc_y1)+\"px\"); ");
		str.append("}) ;");
		str.append("} ");
		str.append("} ); ");
		str.append("$(\".headDiv\").mouseup( ");
		str.append("function(event){ ");
		str.append(" $(document).unbind(\"mousemove\"); ");
		str.append(" }); ");
		}
		
		str.append("$(function() {");
		str.append("$(window).scroll(function() {");
		str.append("var top = $(window).scrollTop();");
		
		str.append("var o = document.getElementById(\""+this.getId()+"\");");
		//str.append("alert('123');");
		//愈加會愈大，不能用
		//str.append("var a = o.style.top;");
		//str.append("a= a.replace(/px/ig, \"\");");
		//str.append("alert((parseInt(a)+parseInt(top)));");
		str.append("o.style.top = (100+parseInt(top)) + \"px\";");
		str.append("});");
		str.append("});");
		//str.append("divAloneCoordinate(\""+this.getId()+"\")");
		str.append("});");
		str.append("</script>");
		writer.println(str.toString());
		
	}


	
	@Override
	protected void beforeRender() {
		super.beforeRender();
			Map<String, String> map = new HashMap<String, String>();
			map.put("display", "none");
			map.put("position", "absolute");
			map.put("z-index", "1");
			map.put("overflow","hidden");
			map.put("width", "400px");
			map.put("height", "600px");
			map.put("left", "25%");
			map.put("top", "15%");
		
		this.setAttribute("style", map);
		
		String style = this.getTag().getAttribute("style");
		if(style != null){
			String[] ar = style.split(";");
			for(String s : ar){
				String[] array = s.split(":");
				if(array.length>1){
					addStyle(array[0].trim(), array[1].trim());
				}
			}
		}
		this.setTagAttribute("style", getStyleCode());
	}
	


	public void show(){
		setAttribute("visible", "true");
		if(getTag().getAttribute("rabbit:animation") ==null)
			getPage().executeScript("showLoading();$(\"#"+this.getId()+"\").show();");
		else
			getPage().executeScript("showLoading();showAnimation(\""+this.getId()+"\");");
	}
	public void hide(){
		setAttribute("visible", null);
		if(getTag().getAttribute("rabbit:animation") ==null)
			getPage().executeScript("hideLoading();$(\"#"+this.getId()+"\").hide();");
		else
			getPage().executeScript("hideAnimation(\""+this.getId()+"\");");
	}
	
	
	
	public void addStyle(String key, String value){
		Map<String, String> map = (Map)this.getAttribute("style");
		if(map == null){
			map = new HashMap<String, String>();
			map.put("display", "none");
			map.put("position", "absolute");
			map.put("z-index", "1");
			map.put("overflow","hidden");
			map.put("width", "400px");
			map.put("height", "600px");
			map.put("left", "25%");
			map.put("top", "15%");
			

		}
		map.put(key, value);
		
		this.setAttribute("style", map);
	}
	
	private String getStyleCode(){
		StringBuffer s = new StringBuffer();
		Map<String, String> map = (Map<String, String>)this.getAttribute("style");
		for(String k : map.keySet()){
			
			s.append(k);
			s.append(":");
			s.append(map.get(k));
			s.append(";");
		}
		return s.toString();
	}
}