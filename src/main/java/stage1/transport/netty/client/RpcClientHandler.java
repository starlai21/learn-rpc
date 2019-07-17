package stage1.transport.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import stage1.transport.model.RpcResponse;

public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        System.out.println(response.getRequestId());//处理响应
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}