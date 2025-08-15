package com.sparetime.service;

import com.sparetime.TestProto;
import com.sparetime.TestServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 下午4:48
 * @desc
 */
public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void test(TestProto.TestRequest request, StreamObserver<TestProto.TestResponse> responseObserver) {
        System.out.println("name = " + request.getName());
        TestProto.TestResponse.Builder reponseBuilder = TestProto.TestResponse.newBuilder();
        TestProto.TestResponse response = reponseBuilder.setResult("收到客户端的消息：" + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
