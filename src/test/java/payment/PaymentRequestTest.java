package payment;

import org.fortumo.Constants;
import org.fortumo.controller.StartupServlet;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author asmaasadek
 * This is an integration test responsible for use case (Receive Payment Notification)
 * Application should be up and running on port 8080 in order for this test to success
 *
 */
public class PaymentRequestTest extends JerseyTest {

	Client testClient = Client.create();

	@Override
	protected Application configure() {
		return new ResourceConfig(StartupServlet.class);
	}

	@Test
	public void givenPaymentNotification_whenCorrectRequest_thenResponseIsOk() {
		WebResource webResource = testClient.resource("http://localhost:8080/payment/api/v1/sms?")
				.queryParam(Constants.MSG_ID, "1234").queryParam(Constants.OPERATOR, "Etisalat")
				.queryParam(Constants.RECEIVER, "13011").queryParam(Constants.SENDER, "%2B37255555555")
				.queryParam(Constants.TEXT, "TXT COINS").queryParam(Constants.TIME_STAMP, "2019-09-03+12%3A32%3A13");

		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);

		assertEquals("Should return status 200", 200, response.getStatus());
		assertEquals("Should return response OK", "Ok", response.getEntity(String.class));
	}

}
