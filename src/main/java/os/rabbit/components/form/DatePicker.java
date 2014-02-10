package os.rabbit.components.form;

import java.io.PrintWriter;

import os.rabbit.IRender;
import os.rabbit.parser.Tag;

/**
 * @author homegowhat
 *
 */
@Deprecated
public class DatePicker extends TextBox {

	public DatePicker(Tag tag) {
		super(tag);
	}

	@Override
	protected void afterBuild() {
		super.afterBuild();
//		this.setTagAttributeModifier("class", true);
//		this.setTagAttributeModifier("y_l", true);
//		this.setTagAttributeModifier("m_l", true);
//		this.setTagAttributeModifier("d_l", true);
//		this.setTagAttributeModifier("y_p_l", true);
//		this.setTagAttributeModifier("m_p_l", true);
//		this.setTagAttributeModifier("d_p_l", true);
//		this.setTagAttributeModifier("seperate", true);
//		this.setTagAttributeModifier("nf", true);
//		this.setTagAttributeModifier("birthday", true);
		
//class="ymd_picker" y_l="年" m_l="月" d_l="日" y_p_l="選擇年" m_p_l="選擇月" d_p_l="選擇日" seperate="-"
		
		getPage().addScriptImport("ymd_picker", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRequest().getContextPath() + "/rbt/ymd_picker.js");
			}
		});
		getPage().addCSSImport("ymdp_css", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				writer.write(getPage().getRequest().getContextPath() + "/rbt/css/ymd_picker.css");
			}
		});
		
	}
	
	

	@Override
	protected void beforeRender() {
		// TODO Auto-generated method stub
		super.beforeRender();
		this.setTagAttribute("class", "ymd_picker");
	}

	@Override
	public void renderComponent(PrintWriter writer) {
		super.renderComponent(writer);
		
		StringBuffer str = new StringBuffer();
		str.append("<script>function yp_over(){");
		String ypOver = (String)getAttribute("yp_over");
		if(ypOver != null){
			str.append(ypOver);
		}
		str.append("}");
		str.append("function mp_over(){");
		String mpOver = (String)getAttribute("mp_over");
		if(mpOver != null){
			str.append(mpOver);
		}
		str.append("}");
		str.append("function dp_over(){");
		String dpOver = (String)getAttribute("dp_over");
		if(dpOver != null){
			str.append(dpOver);
		}
		str.append("}");
		str.append("</script>");
		writer.println(str.toString());
	}
	
	//年這個picker的選項多少、長度
	
	//年期顯示單位(預設:/)
	public void setYearLabel(String yl){
		this.setTagAttribute("y_l", yl);
	}
	//月期顯示單位(預設:/)
	public void setMonthLabel(String ml){
		this.setTagAttribute("m_l", ml);
	}
	//日期顯示單位(預設:空字串)
	public void setDayLabel(String dl){
		this.setTagAttribute("d_l", dl);
	}
	//年份選單的提示(預設:Year)
	public void setYearPickerLabel(String ypl){
		this.setTagAttribute("y_p_l", ypl);
	}
	//月份選單的提示(預設:Month)
	public void setMonthPickerLabel(String mpl){
		this.setTagAttribute("m_p_l", mpl);
	}
	//日期選單的提示(預設:Day)
	public void setDayPickerLabel(String dpl){
		this.setTagAttribute("d_p_l", dpl);
	}
	//日期的分隔符號(預設:/)
	public void setSeperate(String seperate){
		this.setTagAttribute("seperate", seperate);
		this.setAttribute("seperate", seperate);
	}
	public String getSeperate(){
		String s = (String)this.getAttribute("seperate");
		if(s == null)
			return "/";
		else
			return s;
	}
	
	//選完後是否前往下個焦點(預設:true)
	public void setNextFor(boolean nf){
		if(nf)
			this.setTagAttribute("nf", null);
		else
			this.setTagAttribute("nf", "false");
	}
	//年選單完要執行的javascript
	public void setYearOverScript(String yp_over){
			setAttribute("yp_over", yp_over);
	}
	//月選單完要執行的javascript
	public void setMonthOverScript(String mp_over){
			setAttribute("mp_over", mp_over);
	}
	//日選單完要執行的javascript
	public void setDayOverScript(String dp_over){
			setAttribute("dp_over", dp_over);
	}
	//是否為生日的選單(預設:false)
	public void setBirthdayPicker(boolean b){
		if(!b)
			this.setTagAttribute("birthday", null);
		else
			this.setTagAttribute("birthday", "true");
	}
	
}
