package io.pivotal.pcf.ers.recipes.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class SayHelloService {
   
	private Log log = LogFactory.getLog(SayHelloService.class);
	
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
  
    @HystrixCommand(fallbackMethod = "sayHelloFallback")
    public String sayHello(String toWho) {
    	    	
        try{
            return restTemplate.getForObject("http://HELLO-SERVER/hello?name={name}", String.class, toWho);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String sayHelloFallback(String toWho) {
    	log.info("fallback called");
        return String.format("Error, can't say hello to %s", toWho);
    }

}
