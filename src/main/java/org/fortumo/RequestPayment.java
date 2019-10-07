package org.fortumo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.fortumo.handlers.PaymentHandlerToMerchant;

@Path("/api/v1")
public class RequestPayment {

	private final static Logger LOGGER = Logger.getLogger(RequestPayment.class.getName());

	@GET
	@Path("/sms")
	@Produces(MediaType.TEXT_PLAIN)
	public Response receivePaymentNotification(@QueryParam("message_id") String message_id,
			@QueryParam("sender") String sender, @QueryParam("text") String text,
			@QueryParam("receiver") String receiver, @QueryParam("operator") String operator,
			@QueryParam("timestamp") String timestamp

	) throws InterruptedException {
		RequestDTO request = new RequestDTO(message_id, sender, receiver, timestamp, operator, text);
		PaymentHandlerToMerchant.enqueue(request);
		LOGGER.info("Request received=== " + request.toString());
		return Response.status(200).entity("Ok").build();
	}

}
