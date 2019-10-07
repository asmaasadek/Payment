package org.fortumo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class StartupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Properties bundle = new Properties();

	private static ThreadPoolManager manager;
	public static Client merchantClient = null;
	public static Client providerClient = null;

	public StartupServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// Read application.properties file
			InputStream input = config.getServletContext()
					.getResourceAsStream("/WEB-INF/classes/application.properties");
			bundle.load(input);

			// initialize thread pool
			int numOfMerchantThreads = Integer.parseInt(getFromBundle("numOfMerchantThreads"));
			int numOfProviderThreads = Integer.parseInt(getFromBundle("numOfProviderThreads"));
			String userName = getFromBundle("userName").trim();
			String password = getFromBundle("password").trim();

			manager = ThreadPoolManager.getInstance(numOfMerchantThreads, numOfProviderThreads);
			manager.start();

			// Create API Client
			merchantClient = Client.create();
			providerClient = Client.create();
			providerClient.addFilter(new HTTPBasicAuthFilter(userName, password));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServletException("Cannot read Application.properties file.", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public static String getFromBundle(String key) {
		return bundle.getProperty(key);
	}

	@Override
	public void destroy() {
		if (manager != null) {
			manager.stop();
		}
	}
}
