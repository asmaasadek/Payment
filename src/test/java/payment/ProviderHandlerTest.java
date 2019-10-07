package payment;

import static org.junit.Assert.assertEquals;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.fortumo.RequestDTO;
import org.fortumo.controller.StartupServlet;
import org.fortumo.handlers.ProviderHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * 
 * @author asmaasadek
 * This is a unit test for the use case (Send Reply Message)
 *
 */
public class ProviderHandlerTest extends JerseyTest {

	Client testClient = Client.create();

	@Override
	protected Application configure() {
		return new ResourceConfig(StartupServlet.class);
	}

	@Test
	public void givenSendReply_whenCorrectRequest_thenResponseIsOk() throws ServletException {
		RequestDTO request = new RequestDTO("e39ce00e-f8b5-4b0b-96ce-d68f94525704", "%2B37255555555", "13011",
				"2019-09-03+12%3A32%3A13", "Etisalat", "TXT+COINS");
		
		request.setMessageFromMerchant("Hello Test Back!");

		StartupServlet.providerClient = Client.create();
		StartupServlet.providerClient.addFilter(new HTTPBasicAuthFilter("fortumo", "topsecret"));

		int status = ProviderHandler.handle(request);

		assertEquals("Should return status 200", 200, status);
	}

}
