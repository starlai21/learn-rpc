package stage1.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import stage1.transport.model.RpcRequest;
import stage1.transport.model.RpcResponse;
import stage1.transport.netty.decoder.RpcDecoder;
import stage1.transport.netty.encoder.RpcEncoder;

public class RpcNettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel channel) {

                    ChannelPipeline pipline = channel.pipeline();
                    pipline.addLast(new RpcDecoder(RpcRequest.class));
                    pipline.addLast(new RpcEncoder(RpcResponse.class));
                    pipline.addLast(new RpcServerHandler());

                }
            });

            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind("127.0.0.1", 8087).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
