package stage1.transport.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import stage1.serialization.Serialization;
import stage1.serialization.impl.Hessian2Serialization;

import java.io.IOException;
import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clz;

    Serialization serialization = new Hessian2Serialization();

    public RpcDecoder(Class<?> clz){
        this.clz = clz;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IOException {
        if(byteBuf.readableBytes() < 4){
            return;
        }

        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();

        if(byteBuf.readableBytes() < length){
            byteBuf.markReaderIndex();
            return;
        }

        byte [] data = new byte[length];

        byteBuf.readBytes(data);
        list.add(serialization.deserialize(data, clz));
    }
}
