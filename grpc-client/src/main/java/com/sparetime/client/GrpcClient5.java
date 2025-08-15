package com.sparetime.client;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 下午3:11
 * @desc
 */
public class GrpcClient5 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            HelloServiceGrpc.HelloServiceStub helloService = HelloServiceGrpc.newStub(managedChannel);
            StreamObserver<HelloProto.HelloRequest> requestObserver = helloService.clientStream(new StreamObserver<>() {
                @Override
                public void onNext(HelloProto.HelloResponse response) {
                    System.out.println("收到服务端发送的消息：" + response.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    System.out.println("服务端发送了结束信号");
                }
            });
            for (int i = 0; i < 10; i++) {
                HelloProto.HelloRequest.Builder requestBuilder = HelloProto.HelloRequest.newBuilder();
                HelloProto.HelloRequest request = requestBuilder.setName("SpareTime" + LocalDateTime.now()).build();
                requestObserver.onNext(request);
                Thread.sleep(1000);
            }
            requestObserver.onCompleted();
            managedChannel.awaitTermination(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            managedChannel.shutdown();
        }
    }

}
