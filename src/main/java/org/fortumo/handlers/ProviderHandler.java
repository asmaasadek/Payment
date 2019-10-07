package org.fortumo.handlers;

import org.apache.log4j.Logger;
import org.fortumo.Constants;
import org.fortumo.RequestDTO;
import org.fortumo.controller.StartupServlet;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author asmaasadek 
 * This class is responsible for handling requests to Provider for sending the reply message back
 * 
 */
public class ProviderHandler {

	private final static Logger LOGGER = Logger.getLogger(ProviderHandler.class.getName());

	public static int handle(RequestDTO request) {
		ClientResponse response = null;
		String msgId = request.getMsgId();
		String message = request.getMessageFromMerchant();
		if (message.isBlank()) {
			message = Constants.ERROR_MESSAGE_REPLY;
		}
		WebResource webResource = StartupServlet.providerClient.resource(Constants.MESSAGE_REPLY_URL)
				.queryParam(Constants.MESSAGE, message).queryParam(Constants.MESSAGE_ID, request.getMsgId())
				.queryParam(Constants.OPERATOR, request.getOperator())
				.queryParam(Constants.RECEIVER, request.getReceiver());

		LOGGER.info("Send Message Reply for MessageId " + msgId);
		try {
			response = webResource.accept(Constants.CONTENT_TYPE).accept(Constants.UTF_8).get(ClientResponse.class);
			LOGGER.info("Response Status for MessageId " + msgId + " " + response.getStatus());

			if (response.getStatus() == 200) {
				String output = response.getEntity(String.class);
				LOGGER.info("Message Reply Response for MessageId " + msgId + " " + output);
			} else {
				LOGGER.info("Provider Request has failed for MessageId " + msgId);
				LOGGER.info("Reply Message with failure To User " + message);
			}
		} catch (ClientHandlerException e) {
			LOGGER.error("Unable to connect to Provider with MessageId " + msgId, e);
		}
		
		return response.getStatus();

	}

}
