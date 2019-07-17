package stage1.serialization.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import stage1.serialization.Serialization;
import stage1.transport.model.RpcRequest;
import stage1.transport.model.RpcResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Hessian2Serialization implements Serialization {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArrayOutputStream);
        output.writeObject(object);
        output.flush();
        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
        return (T) input.readObject(clz);
    }

    public static void main(String[] args) throws IOException {
        Serialization serialization = new Hessian2Serialization();

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(1L);
        byte[] requestBody  = serialization.serialize(rpcRequest);

        RpcRequest rpcRequest2 = serialization.deserialize(requestBody, RpcRequest.class);

        System.out.println(rpcRequest2.getRequestId());
    }
}
