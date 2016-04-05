package io.pivotal.pcf.ers.recipes.client.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import io.pivotal.pcf.ers.recipes.client.service.UiService;
import io.pivotal.pcf.ers.recipes.client.service.SayHelloService;

/**
 * BasicController
 * 
 * The sole purpose is to be the "controller" for the MVC pattern. Keep it
 * simple with no major logic in it.
 * 
 * @author mborges
 *
 */
@Controller
public class UiController {

	private Log log = LogFactory.getLog(UiController.class);

	@Autowired
	private UiService uiService;

	@Autowired
	private SayHelloService sayHelloService;

	/**
	 * INDEX
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public String index(Model model) throws Exception {
		log.info("default page");
		addAppEnv(model);
		return "index";
	}

	/**
	 * ui
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ui")
	public String ui(Model model) throws Exception {
		log.info("ui page");
		addAppEnv(model);
		return "ui";
	}

	/**
	 * hello-server
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hello-server")
	public String sayHello(Model model) throws Exception {
		log.info("hello-server page");
		model.addAttribute("greeting", sayHelloService.sayHello("test"));
		return "hello-server";
	}
	

	/**
	 * server-jpa
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/server-jpa")
	public String serverJpa(Model model) throws Exception {
		log.info("server-jpa page");
		addAppEnv(model);
		return "server-jpa";
	}

	///////////////////////////////////////
	// Helper Methods
	///////////////////////////////////////

	private void addAppEnv(Model model) throws Exception {

		Map<String, Object> modelMap = uiService.addAppEnv();
		model.addAllAttributes(modelMap);
	}

}
