package com.sparetime.client;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.sparetime.TestProto;
import com.sparetime.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Qiu Yifan
 * @date 2025/8/15 下午4:53
 * @desc
 */
public class GrpcClient7 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        try {
            TestProto.TestRequest.Builder requestBuilder = TestProto.TestRequest.newBuilder();
            TestProto.TestRequest request = requestBuilder.setName("SpareTime").build();
            TestServiceGrpc.TestServiceFutureStub testService = TestServiceGrpc.newFutureStub(managedChannel);
            ListenableFuture<TestProto.TestResponse> listenableFuture = testService.test(request);
            // 下面这种方式本质上是同步调用
//            TestProto.TestResponse response = listenableFuture.get();
//            System.out.println(response.getResult());

            // 下面这种方式是异步调用，但是没有实战意义，因为无法获取消息体
//            listenableFuture.addListener(() -> {
//                System.out.println("异步收到服务端返回的消息");
//            }, Executors.newCachedThreadPool());

            // 常用的异步调用形式
            Futures.addCallback(listenableFuture, new FutureCallback<>() {
                @Override
                public void onSuccess(TestProto.TestResponse response) {
                    System.out.println("异步收到服务端返回的消息：" + response.getResult());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }, Executors.newCachedThreadPool());
            managedChannel.awaitTermination(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            managedChannel.shutdown();
        }
    }

}
