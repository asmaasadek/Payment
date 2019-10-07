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
 * This class is responsible for handling requests to Merchant to Forward notifications from Provider
 *
 */
public class MerchantHandler {

	private final static Logger LOGGER = Logger.getLogger(MerchantHandler.class.getName());

	public static String getRequestAsJson(RequestDTO request) {
		String sender = "";
		if (!request.getSender().isBlank()) {
			sender = request.getSender().substring(1);
		}

		String input = "{\"sender\":" + "\"" + sender + "\"" + ",\"operator\":" + "\"" + request.getOperator() + "\""
				+ ",\"message\":" + "\"" + request.getText() + "\"" + "," + "\"shortcode\":" + "\""
				+ request.getReceiver() + "\"" + ",\"keyword\":" + "\"" + request.getKeyword() + "\""
				+ ",\"transaction_id\": " + "\"" + request.getTransactionId() + "\"" + "}";

		return input;
	}

	public static String handle(RequestDTO request) {
		ClientResponse response = null;
		String msgReply = "";

		WebResource webResource = StartupServlet.merchantClient.resource(request.getMerchantUrl());
		LOGGER.info("Forward Notification To Merchant started with TransactionId " + request.getTransactionId()
				+ " and MsgId " + request.getMsgId());
		try {
			response = webResource.type(Constants.CONTENT_TYPE).post(ClientResponse.class, request.getInput());
			LOGGER.info("Merchant Response Status for MessageId " + request.getMsgId() + " " + response.getStatus());

			if (response.getStatus() == 200) {
				msgReply = response.getEntity(String.class);
				LOGGER.info("Merchant Response Successful for MessageId " + request.getMsgId() + " and returned Reply "
						+ msgReply);
			} else {
				LOGGER.info("Merchant Request has Failed for MessageId " + request.getMsgId());
			}
		} catch (ClientHandlerException e) {
			LOGGER.error("Unable to connect to Merchant with MessageId" + request.getMsgId(), e);
		}

		return msgReply;
	}

}
