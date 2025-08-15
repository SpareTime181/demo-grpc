package com.sparetime.client;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 下午12:01
 * @desc
 */
public class GrpcClient3 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            HelloProto.HelloRequest.Builder requestBuilder = HelloProto.HelloRequest.newBuilder();
            HelloProto.HelloRequest request = requestBuilder.setName("SpareTime").build();
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);
            Iterator<HelloProto.HelloResponse> iterator = helloService.serverStream(request);
            while (iterator.hasNext()) {
                HelloProto.HelloResponse response = iterator.next();
                System.out.println(response.getResult());
            }
        } finally {
            managedChannel.shutdown();
        }
    }

}