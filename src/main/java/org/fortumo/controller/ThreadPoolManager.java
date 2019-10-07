package org.fortumo.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.fortumo.handlers.PaymentHandlerToMerchant;
import org.fortumo.handlers.PaymentHandlerToProvider;

public class ThreadPoolManager {

	private final int merchantPoolSize;
	private final int providerPoolSize;
	private final ThreadPoolExecutor merchantExecutor;
	private final ThreadPoolExecutor providerExecutor;

	private static ThreadPoolManager self = null;

	/**
	 * Create two executers with configured number of threads
	 * Each Executer should be working on a handler(Provider and Merchant)
	 * @param merchantPoolSize
	 * @param providerPoolSize
	 */
	private ThreadPoolManager(int merchantPoolSize, int providerPoolSize) {
		this.merchantPoolSize = merchantPoolSize;
		this.providerPoolSize = providerPoolSize;
		merchantExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(merchantPoolSize);
		providerExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(providerPoolSize);
	}

	public static ThreadPoolManager getInstance(int merchantPoolSize, int providerPoolSize) {
		if (self == null) {
			self = new ThreadPoolManager(merchantPoolSize, providerPoolSize);
		}

		return self;
	}

	public void start() {
		Runnable runnable = null;
		for (int i = 0; i < merchantPoolSize; i++) {
			runnable = new PaymentHandlerToMerchant();
			merchantExecutor.execute(runnable);
		}

		for (int i = 0; i < providerPoolSize; i++) {
			runnable = new PaymentHandlerToProvider();
			providerExecutor.execute(runnable);
		}
	}

	public void stop() {
		merchantExecutor.shutdown();
		providerExecutor.shutdown();
	}
}
