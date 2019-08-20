package com.xiachao.rpc.channel.handler;

import com.xiachao.rpc.coder.RpcMesageEncoder;
import com.xiachao.rpc.coder.RpcMessageDcoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
public class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new RpcMesageEncoder());
        pipeline.addLast(new RpcMessageDcoder());
        pipeline.addLast(new ChannelRegisterHandler());
        pipeline.addLast(new RpcMessageHandler());
        //pipeline.addLast(new LoggingHandler(LogLevel.INFO));

       // pipeline.addLast(new ReqResHandler2());

        //pipeline.addLast(new LineBasedFrameDecoder(1));
//        pipeline.addLast(new LengthFieldPrepender(4, false));
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
//                0, 4, 0, 4));

//
//        pipeline.addLast(new ObjectSerializerEncoder());
//        pipeline.addLast(new ObjectSerializerDecoder());
    }
}
