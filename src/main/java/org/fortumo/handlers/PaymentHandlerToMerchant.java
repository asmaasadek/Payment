package org.fortumo.handlers;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.fortumo.RequestDTO;
import org.fortumo.controller.StartupServlet;

/**
 * 
 * @author asmaasadek 
 * This class is responsible for a thread working on the Merchant handler
 *
 */
public class PaymentHandlerToMerchant implements Runnable {

	private static BlockingQueue<RequestDTO> queue = new LinkedBlockingQueue<RequestDTO>();
	private final static Logger LOGGER = Logger.getLogger(PaymentHandlerToMerchant.class.getName());

	public static void enqueue(RequestDTO requestDto) {
		queue.offer(requestDto);
	}

	@Override
	public void run() {
		while (true) {
			RequestDTO request;
			int sleepTime = 0;
			while ((request = queue.poll()) != null) {
				LOGGER.info("DEQUEUING To Merchant Thread=== " + request.toString());
				String keyword = "";
				String msg = request.getText();
				if (!msg.isBlank()) {
					keyword = msg.substring(0, msg.indexOf(" "));
					request.setKeyword(keyword);
				}

				sleepTime = Integer.parseInt(StartupServlet.getFromBundle("merchantSleepTime"));
				keyword = StartupServlet.getFromBundle(keyword + "_URL");

				if (keyword != null && !keyword.isEmpty()) {
					String uniqueID = UUID.randomUUID().toString();
					String requestJson = MerchantHandler.getRequestAsJson(request);
					request.setInput(requestJson);
					request.setMerchantUrl(keyword);
					request.setTransactionId(uniqueID);
					String message = MerchantHandler.handle(request);
					request.setMessageFromMerchant(message);
					PaymentHandlerToProvider.enqueue(request);
				} else {
					LOGGER.info("Keyword mapping not found Or Text could be blank.");
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
