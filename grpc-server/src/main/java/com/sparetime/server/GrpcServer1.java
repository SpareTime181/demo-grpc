package com.sparetime.server;

import com.sparetime.service.HelloServiceImpl;
import com.sparetime.service.TestServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 上午10:13
 * @desc
 */
public class GrpcServer1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000);
        serverBuilder.addService(new HelloServiceImpl());
        serverBuilder.addService(new TestServiceImpl());
        Server server = serverBuilder.build();
        server.start();
        server.awaitTermination();
    }

}
