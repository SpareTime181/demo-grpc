package com.sparetime.client;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 上午10:18
 * @desc
 */
public class GrpcClient1 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        try {
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
            HelloProto.HelloRequest.Builder requestBuilder = HelloProto.HelloRequest.newBuilder();
            HelloProto.HelloRequest request = requestBuilder.setName("Tom").build();
            HelloProto.HelloResponse response = helloService.hello(request);
            System.out.println(response.getResult());
        } finally {
            managedChannel.shutdown();
        }
    }

}
