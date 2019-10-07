package insee.api.launcher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;

public class Launcher {

	private static final Logger logger = Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/** opensiret */
		String token = null;
		String response = null;
		try {
			// hack certif
			SshUtil.createTrustedManager();
			// http client
			// HttpClient
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).build();

			// HttpPost Request
			HttpPost postRequest = new HttpPost("https://api.insee.fr/token");
			String encodedBytes = Base64.getEncoder().encodeToString(("6_ttyuk3QfleC7fj64IUF05hVUMa:IUqxBiG3mLhsG4wrd1ewCwYeFRka").getBytes());
			postRequest.addHeader("Authorization", "Basic " + encodedBytes);
			postRequest.setEntity(new StringEntity("grant_type=client_credentials", StandardCharsets.UTF_8));
			HttpResponse httpPostResponse = httpClient.execute(postRequest);
			token = FileUtil.dump(httpPostResponse.getEntity().getContent());

			// HttpGet Request
			HttpGet getRequest = new HttpGet("https://api.insee.fr/entreprises/sirene/V3/siret/85318202000018");
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", "Bearer " + "94584011-403f-3a2d-b5a4-c1221f614664");
			HttpResponse httpgetResponse = httpClient.execute(getRequest);
			response = FileUtil.dump(httpgetResponse.getEntity().getContent());

		} catch (Exception e) {
			logger.error("", e);
		}

		logger.info("\ntoken:" + token + "\nresponse:" + response);

	}

}
