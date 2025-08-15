package com.sparetime.service;

import com.google.protobuf.ProtocolStringList;
import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 上午10:08
 * @desc
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        System.out.println("server get name: " + name);
        String result = "Hello " + name + "!";
        HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
        responseBuilder.setResult(result);
        HelloProto.HelloResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void hello1(HelloProto.HelloRequest1 request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        ProtocolStringList nameList = request.getNameList();
        for (String name : nameList) {
            System.out.println(name);
        }
        String result = "Have got name list";
        HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
        HelloProto.HelloResponse response = responseBuilder.setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void serverStream(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        System.out.println("name = " + name);
        for (int i = 0; i < 10; i++) {
            HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
            HelloProto.HelloResponse response = builder.setResult(LocalDateTime.now().toString()).build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> clientStream(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        return new StreamObserver<>() {

            final List<HelloProto.HelloRequest> requests = new ArrayList<>();

            @Override
            public void onNext(HelloProto.HelloRequest request) {
                System.out.println("收到客户端消息：" + request.getName());
                requests.add(request);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("收到客户端结束消息发送消息，总共发送消息数：" + requests.size());
                HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
                HelloProto.HelloResponse response = responseBuilder.setResult("总共收到消息数量：" + requests.size()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> biDirectionStream(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        return new StreamObserver<>() {

            final List<HelloProto.HelloRequest> requests = new ArrayList<>();

            @Override
            public void onNext(HelloProto.HelloRequest request) {
                System.out.println("服务端收到消息：" + request.getName());
                requests.add(request);
                HelloProto.HelloResponse.Builder responseBuilder = HelloProto.HelloResponse.newBuilder();
                HelloProto.HelloResponse response = responseBuilder.setResult("服务端收到消息数量：" + requests.size()).build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("收到客户端的所有消息");
                responseObserver.onCompleted();
            }
        };
    }
}