package io.pivotal.pcf.ers.recipes.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class SayHelloService {
   
	private Log log = LogFactory.getLog(SayHelloService.class);
	
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    @Autowired
    private DiscoveryClient discoveryClient;

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("hello-server");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    @HystrixCommand(fallbackMethod = "sayHelloFallback")
    public String sayHello(String toWho) {
    	
    	log.info("######################################### " + serviceUrl());
    	
        try{
            return restTemplate.getForObject("http://hello-server/hello?name={name}", String.class, toWho);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String sayHelloFallback(String toWho) {
        return String.format("Error, can't say hello to %s", toWho);
    }

}
