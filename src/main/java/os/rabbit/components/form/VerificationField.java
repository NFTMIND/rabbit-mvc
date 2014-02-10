package os.rabbit.components.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;

import os.rabbit.ITrigger;
import os.rabbit.RenderInterruptedException;
import os.rabbit.parser.Tag;

public class VerificationField extends TextBox {

	public VerificationField(Tag tag) {
		super(tag);

	}
	
	@Override
	protected void afterBuild() {
		getPage().addTrigger(getId() + "$getImage", new ITrigger() {
			
			@Override
			public void invoke() {
				try {
					
					OutputStream out = getPage().getResponse().getOutputStream();
					BufferedImage image = render(getCode());
					ImageIO.write(image, "png", out);
					throw new RenderInterruptedException();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
	@Override
	public void renderComponent(PrintWriter writer) {
		super.renderComponent(writer);
		writer.write("<img id=\""+getId()+"_image\" src=\""+getPage().getRequestURI()+"?rbtType=INVOKE&triggerId=" + getId() + "$getImage\" />");

	}
	
	public String getCode() {
		return (String)getPage().getRequest().getSession().getAttribute("RandomCodeVerificationField.code$" + getId());
	}
	
	public void setCode(String code) {
		getPage().getRequest().getSession().setAttribute("RandomCodeVerificationField.code$" + getId(), code);
	}
	

	public void repaintImage() {
		generateCode();
		PrintWriter writer = new PrintWriter(getPage().getWriter());
		writer.write("<html id=\"" + getId() + "_image\">");
		writer.write("<![CDATA[");
		writer.write("<img id=\""+getId()+"_image\" src=\""+getPage().getRequestURI()+"?rbtType=INVOKE&triggerId=" + getId() + "$getImage\" />");

		writer.write("]]>");
		writer.write("</html>");

		writer.write("<script>");
		writer.write("<![CDATA[");
		renderScript(writer);
		writer.write("]]>");
		writer.write("</script>");
	}
	private void generateCode() {
		// 生成隨機類
		Random random = new Random();
		// 取隨機產生的認證碼(4位元數位)

		String pwdstr = "0123456789";
		StringBuffer sRand = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			char rand = pwdstr.charAt(random.nextInt(10));
			sRand.append(rand);
			// 將認證碼顯示到圖像中

		}
		// 將認證碼存入SESSION
		setCode(sRand.toString());
	}
	@Override
	protected void beforeRender() {
		generateCode();
	}

	public BufferedImage render(String code) {
		// 隨機數
		// String[] pwdstr =
		// {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

		// 設定頁面不暫存
		// response.setHeader("Pragma", "No-cache");
		// response.setHeader("Cache-Control", "no-cache");
		// response.setDateHeader("Expires", 0);

		// 在記憶体中創建圖像
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 獲取圖形上下文
		Graphics g = image.getGraphics();

		// 設定背景顏色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 設定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 畫邊框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 隨機產生155條干擾線，使圖像中的認證碼不易被其他程式探測到
		g.setColor(getRandColor(160, 200));

		Random random = new Random();

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int loop = 0; loop < code.length(); loop++) {
			String charCode = code.substring(loop, loop + 1);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 調用函數出來的顏色相同，可能是因為種子太接近，所以只能直接生成
			g.drawString(charCode, 13 * loop + 6, 16);
		}

		// 圖像生效
		g.dispose();

		return image;

	}

	public Color getRandColor(int fc, int bc) {// 給定範選獲得隨機顏色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
