package com.sparetime.grpcbootserver.service;

import com.sparetime.HelloProto;
import com.sparetime.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author Qiu Yifan
 * @date 2025/8/20 下午3:51
 * @desc
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        System.out.println("name = " + name);
        responseObserver.onNext(HelloProto.HelloResponse.newBuilder().setResult("result is OK").build());
        responseObserver.onCompleted();
    }
}
