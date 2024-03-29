package os.rabbit.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RecvServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String url = "https://bycdn5-i.akamaihd.net/core" + req.getPathInfo();
		
		
		SSLContextBuilder builder = new SSLContextBuilder();
	    try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			 SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
			            builder.build());
			    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
			            sslsf).build();

			    HttpGet httpGet = new HttpGet(url);
			    CloseableHttpResponse response = httpclient.execute(httpGet);
			    try {
			
			        HttpEntity entity = response.getEntity();
			       // EntityUtils.consume(entity);
			        resp.setHeader("ContentType", "application/swf");
			        IOUtils.copy(entity.getContent(), resp.getOutputStream());
			       
			    }
			    finally {
			        response.close();
			    }
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		
	}
}
