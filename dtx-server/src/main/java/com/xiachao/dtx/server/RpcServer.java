package com.xiachao.dtx.server;

import com.xiachao.rpc.channel.handler.ChannelInitHandler;
import com.xiachao.rpc.channel.handler.ChannelRegisterHandler;
import com.xiachao.rpc.channel.handler.RpcMessageHandler;
import com.xiachao.rpc.request.FinishTxReq;
import com.xiachao.rpc.request.JoinTxReq;
import com.xiachao.rpc.request.PingReq;
import com.xiachao.rpc.request.StartTxReq;
import com.xiachao.rpc.request.handler.*;
import com.xiachao.rpc.response.PingRes;
import com.xiachao.rpc.response.handler.PingResHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-04]
 */
@Component
public class RpcServer {

    @PostConstruct
    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Map<Class, ReqResHandler> dispatchMap=new HashMap<>();
        dispatchMap.put(StartTxReq.class,new StartTxReqHandler());
        dispatchMap.put(JoinTxReq.class,new JoinTxReqHandler());
        dispatchMap.put(PingReq.class,new PingReqHandler());
        dispatchMap.put(PingRes.class,new PingResHandler());
        dispatchMap.put(FinishTxReq.class,new FinishTxReqHandler());
        HanlderMap.setDispatchMap(dispatchMap);
        try {
            // 配置服务器的NIO线程租
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitHandler());
//                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                        @Override
//                        protected void initChannel(NioSocketChannel ch) {
//                            ch.pipeline().addLast(new ChannelCacheHandler());
//                        }
//                    });

            ChannelFuture channelFuture = b.bind(1234).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
