package com.sparetime.grpcbootclient.controller;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiu Yifan
 * @date 2025/8/20 下午5:09
 * @desc
 */
@RestController
public class HelloController {

    @GrpcClient("grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloService;

    @GetMapping("/hello")
    public String hello(String name) {
        HelloProto.HelloResponse response = helloService.hello(HelloProto.HelloRequest.newBuilder().setName(name).build());
        return response.getResult();
    }

}