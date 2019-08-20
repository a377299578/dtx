package com.xiachao.dtx.client;

import com.xiachao.rpc.channel.handler.ChannelInitHandler;
import com.xiachao.rpc.channel.handler.RpcMessageHandler;
import com.xiachao.rpc.request.PingReq;
import com.xiachao.rpc.request.handler.HanlderMap;
import com.xiachao.rpc.request.handler.PingReqHandler;
import com.xiachao.rpc.request.handler.ReqResHandler;
import com.xiachao.rpc.response.FinishTxRes;
import com.xiachao.rpc.response.JoinTxRes;
import com.xiachao.rpc.response.PingRes;
import com.xiachao.rpc.response.StartTxRes;
import com.xiachao.rpc.response.handler.FinishTxResHandler;
import com.xiachao.rpc.response.handler.JoinTxResHandler;
import com.xiachao.rpc.response.handler.PingResHandler;
import com.xiachao.rpc.response.handler.StartTxResHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-04]
 */
@Component
public class RpcClient implements ApplicationListener<ContextRefreshedEvent> {

//    @PostConstruct
//    public void init() {
//
//    }
//
//    public void connect(Bootstrap b) throws InterruptedException {
//        new Thread(() -> {
//            try {
//                // 发起异步连接操作
//                ChannelFuture f = b.connect("127.0.0.1", 1234).sync();
//                // 等待客户端链路关闭
//                f.channel().closeFuture().sync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        Map<Class, ReqResHandler> dispatchMap = new HashMap<>();
        dispatchMap.put(StartTxRes.class, new StartTxResHandler());
        dispatchMap.put(JoinTxRes.class, new JoinTxResHandler());
        dispatchMap.put(PingReq.class, new PingReqHandler());
        dispatchMap.put(PingRes.class, new PingResHandler());
        dispatchMap.put(FinishTxRes.class, new FinishTxResHandler());
        HanlderMap.setDispatchMap(dispatchMap);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitHandler());
            //connect(b);
            ChannelFuture f = b.connect("127.0.0.1", 1234).syncUninterruptibly();
            // 等待客户端链路关闭
           // f.channel().closeFuture().sync();
        }  finally {
            // 优雅退出，释放NIO线程组
            //group.shutdownGracefully();
        }
    }
}
