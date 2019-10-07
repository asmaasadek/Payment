package org.fortumo.handlers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.fortumo.RequestDTO;
import org.fortumo.controller.StartupServlet;

/**
 * 
 * @author asmaasadek
 * This class is responsible for a thread working on the Provider handler
 *
 */
public class PaymentHandlerToProvider implements Runnable {

	private static BlockingQueue<RequestDTO> queue = new LinkedBlockingQueue<RequestDTO>();
	private final static Logger LOGGER = Logger.getLogger(PaymentHandlerToProvider.class.getName());

	public static void enqueue(RequestDTO requestDto) {
		queue.offer(requestDto);
	}

	@Override
	public void run() {
		while (true) {
			RequestDTO request;
			int sleepTime = 0;

			while ((request = queue.poll()) != null) {
				LOGGER.info("DEQUEUING To Provider Thread=== " + request.toString());
				sleepTime = Integer.parseInt(StartupServlet.getFromBundle("providerSleepTime"));
				ProviderHandler.handle(request);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
