package payment;

import static org.junit.Assert.assertTrue;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.fortumo.RequestDTO;
import org.fortumo.controller.StartupServlet;
import org.fortumo.handlers.MerchantHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import com.sun.jersey.api.client.Client;

/**
 * 
 * @author asmaasadek
 * This is a unit test for the use case (Forward notification to Merchant)
 *
 */
public class MerchantHandlerTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new ResourceConfig(StartupServlet.class);
	}

	@Test
	public void givenForwardToMerchant_whenCorrectRequest_thenResponseIsOk() throws ServletException {
		RequestDTO request = new RequestDTO("e39ce00e-f8b5-4b0b-96ce-d68f94525704", "%2B37255555555", "13011",
				"2019-09-03+12%3A32%3A13", "Etisalat", "TXT+COINS");
		
		request.setTransactionId("UUID");
		request.setKeyword("TXT");
		
		String URL = "http://bratwurst.fortumo.mobi/api/sms/txt";
		String input = MerchantHandler.getRequestAsJson(request);

		request.setInput(input);
		request.setMerchantUrl(URL);

		StartupServlet.merchantClient = Client.create();
		String reply = MerchantHandler.handle(request);

		assertTrue(!reply.isEmpty());
	}

}
