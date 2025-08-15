package com.sparetime.client;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 下午2:05
 * @desc
 */
public class GrpcClient4 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            HelloProto.HelloRequest.Builder requestBuilder = HelloProto.HelloRequest.newBuilder();
            HelloProto.HelloRequest request = requestBuilder.setName("SpareTime").build();
            HelloServiceGrpc.HelloServiceStub helloService = HelloServiceGrpc.newStub(managedChannel);
            helloService.serverStream(request, new StreamObserver<>() {

                private List<HelloProto.HelloResponse> responses = new ArrayList<>();

                @Override
                public void onNext(HelloProto.HelloResponse response) {
                    System.out.println("get server message: " + response.getResult());
                    responses.add(response);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    System.out.println("The server has finished sending the message, message count: " + responses.size());
                }
            });
            managedChannel.awaitTermination(12, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            managedChannel.shutdown();
        }
    }

}
