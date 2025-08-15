package com.sparetime.client;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 上午10:35
 * @desc
 */
public class GrpcClient2 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            HelloProto.HelloRequest1.Builder requestBuilder = HelloProto.HelloRequest1.newBuilder();
            requestBuilder.addName("Tom");
            requestBuilder.addName("Marry");
            requestBuilder.addName("Scott");
            HelloProto.HelloRequest1 request = requestBuilder.build();
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
            HelloProto.HelloResponse response = helloService.hello1(request);
            System.out.println(response.getResult());
        } finally {
            managedChannel.shutdown();
        }
    }

}
