package com.yp.consumermovie.feign;

import com.yp.consumermovie.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microservice-simple-provider-user")
public interface UserFeignClient {
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);
}
