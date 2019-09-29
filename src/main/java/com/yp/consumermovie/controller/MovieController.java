package com.yp.consumermovie.controller;

import com.yp.consumermovie.feign.UserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yp.consumermovie.entity.User;

@Import(FeignClientsConfiguration.class)
@RestController
public class MovieController {
	@Autowired
	private RestTemplate restTemplate;
	private UserFeignClient userUserFeignClient;
	private UserFeignClient adminUserFeignClient;
//	@Autowired
//	private LoadBalancerClient loadBalancerClient;
//	private static final Logger LOGGER  = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract){
		this.userUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
				.requestInterceptor(new BasicAuthRequestInterceptor("user","password1")).target(UserFeignClient.class,"http://microservice-simple-provider-user/");
		this.adminUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
				.requestInterceptor(new BasicAuthRequestInterceptor("admin","password2")).target(UserFeignClient.class,"http://microservice-simple-provider-user/");
	}


	@GetMapping("/user-user/{id}")
	public User findById(@PathVariable Long id) {
//		return this.restTemplate.getForObject("http://microservice-simple-provider-user/" + id, User.class);
		return this.userUserFeignClient.findById(id);
	}

	@GetMapping("/user-admin/{id}")
	public User findByAdmin(@PathVariable Long id){
		return this.adminUserFeignClient.findById(id);
	}
//
//	@GetMapping("/log-user-instance")
//	public void logUserInstance(){
//		ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-simple-provider-user");
//		MovieController.LOGGER.info("{},{},{}",serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance);
//	}

}
