package io.pivotal.pcf.ers.recipes.client.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.pivotal.pcf.ers.recipes.client.controller.UiController;

/**
 * BasicServices
 * 
 * The purpose of this service class is to have a clear separation from the UI
 * and the client.
 * 
 * This is a RestController and all the UI stuff is done by the MVC controller.
 * 
 * @see UiController
 * 
 * @author mborges
 *
 */
@RestController
public class UiService {

	private Log log = LogFactory.getLog(UiService.class);
	
	@Autowired
	private SayHelloService sayHelloService;

	@RequestMapping("/hello")
	public String sayHello(@RequestParam(value = "name", defaultValue = "World") String toWho) {
		return sayHelloService.sayHello(toWho);
	}

	/**
	 * addAppEnv - Retrieve information about the application
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> addAppEnv() throws Exception {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		String instanceIndex = getVcapApplicationMap().getOrDefault("instance_index", "no index environment variable")
				.toString();
		modelMap.put("instanceIndex", instanceIndex);

		String instanceAddr = System.getenv("CF_INSTANCE_ADDR");
		if (instanceAddr == null) {
			instanceAddr = "running locally";
		}
		modelMap.put("instanceAddr", instanceAddr);

		String applicationName = (String) getVcapApplicationMap().getOrDefault("application_name",
				"no name environment variable");
		modelMap.put("applicationName", applicationName);

		@SuppressWarnings("rawtypes")
		Map services = getVcapServicesMap();
		modelMap.put("applicationServices", services);

		// getting host local address
		try {
			InetAddress ipAddr = InetAddress.getLocalHost();
			modelMap.put("inetaddressLocalhost", ipAddr);
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}

		return modelMap;
	}

	/**
	 * bluegreenRequest - It really just returns the app name.
	 * 
	 * The purpose is to show PCF load balancing between applications that share
	 * routes
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/bluegreen-check")
	public String[] bluegreenRequest() throws Exception {

		List<String> values = new ArrayList<String>();
		values.add((String) getVcapApplicationMap().getOrDefault("application_name", "no name environment variable"));
		values.add(getVcapApplicationMap().getOrDefault("instance_index", "running locally").toString());

		return values.toArray(new String[0]);
	}

	///////////////////////////////////////
	// Helper Methods
	///////////////////////////////////////

	@SuppressWarnings("rawtypes")
	private Map getVcapApplicationMap() throws Exception {
		return getEnvMap("VCAP_APPLICATION");
	}

	@SuppressWarnings("rawtypes")
	private Map getVcapServicesMap() throws Exception {
		return getEnvMap("VCAP_SERVICES");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getEnvMap(String vcap) throws Exception {
		String vcapEnv = System.getenv(vcap);
		ObjectMapper mapper = new ObjectMapper();

		if (vcapEnv != null) {
			Map<String, ?> vcapMap = mapper.readValue(vcapEnv, Map.class);
			return vcapMap;
		}

		log.warn(vcap + " not defined, returning empty Map");
		return new HashMap<String, String>();
	}

}
