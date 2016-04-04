package io.pivotal.pcf.ers.recipes.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Component
public class SayHelloService {
   
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "sayHelloFallback")
    public String sayHello(String toWho) {
        try{
            return restTemplate.getForObject("https://hello-world-server/hello?name={name}", String.class, toWho);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String sayHelloFallback(String toWho) {
        return String.format("Error, can't say hello to %s", toWho);
    }

}
