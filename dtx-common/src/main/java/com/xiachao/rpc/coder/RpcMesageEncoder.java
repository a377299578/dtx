/*
 * Copyright 2017-2019 CodingApi .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiachao.rpc.coder;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.request.PingReq;
import com.xiachao.rpc.request.RpcMessage;
import com.xiachao.rpc.response.PingRes;
import com.xiachao.utils.FstUtil;
import com.xiachao.utils.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/10
 *
 * @author ujued
 */
@ChannelHandler.Sharable
@Slf4j
public class RpcMesageEncoder extends MessageToMessageEncoder<RpcMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage rpcMessage, List<Object> out) throws Exception {
        if (rpcMessage.getBody().getClass().equals(PingReq.class) || rpcMessage.getBody().getClass().equals(PingRes.class)) {

        } else {
            log.info("发送通道:" + JSON.toJSONString(ctx.channel().id()) + " 的信息,类型是：" + rpcMessage.getBody().getClass().toString() + ",信息是" + JSON.toJSONString(rpcMessage));
        }
        byte[] bytes = ProtostuffSerializer.serialize(rpcMessage);
        ByteBuf buffer = Unpooled.buffer(bytes.length);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(bytes[i]);
        }
        out.add(buffer);
    }
}
