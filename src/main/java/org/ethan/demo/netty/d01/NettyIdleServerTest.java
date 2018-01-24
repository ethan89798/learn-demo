package org.ethan.demo.netty.d01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 程序心跳,进行确保连接的通信,及时清理后台已经掉弃的连接
 * @author Ethan Huang
 * @since 2018-01-21 21:22
 */
public class NettyIdleServerTest {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new NettyIdleServerInitilializer());

            ChannelFuture future = bootstrap.bind(8889).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

class NettyIdleServerInitilializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(8, 7, 5));
        pipeline.addLast(new NettyIdleServerHandler());
    }
}

class NettyIdleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventTypeStr = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventTypeStr = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventTypeStr = "写空闲";
                    break;
                case ALL_IDLE:
                    eventTypeStr = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + eventTypeStr);
            ctx.channel().close();
        }
    }
}