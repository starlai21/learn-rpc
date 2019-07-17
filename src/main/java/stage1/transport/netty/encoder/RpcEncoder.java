package stage1.transport.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import stage1.serialization.Serialization;
import stage1.serialization.impl.Hessian2Serialization;

import java.io.IOException;

public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clz;

    Serialization serialization = new Hessian2Serialization();

    public RpcEncoder(Class<?> clz){
        this.clz = clz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws IOException {
        if(clz.isInstance(o)){
            byte[] data = serialization.serialize(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
