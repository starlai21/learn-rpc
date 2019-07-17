package stage1.transport.socket;

import stage1.serialization.Serialization;
import stage1.serialization.impl.Hessian2Serialization;
import stage1.transport.model.RpcRequest;
import stage1.transport.model.RpcResponse;

import java.io.*;
import java.net.Socket;

public class RpcSocketConsumer {

    public static void main(String[] args) throws IOException {
        Serialization serialization = new Hessian2Serialization();

        Socket socket = new Socket("localhost", 8888);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(12345L);

        byte[] requestBody = serialization.serialize(rpcRequest);

        DataOutputStream outputStream = new DataOutputStream(os);

        outputStream.writeInt(requestBody.length);

        outputStream.write(requestBody);

        outputStream.flush();

        DataInputStream inputStream = new DataInputStream(is);
        int responseLength = inputStream.readInt();

        byte[] responseBody = new byte[responseLength];

        inputStream.read(responseBody);


        RpcResponse rpcResponse = serialization.deserialize(responseBody, RpcResponse.class);

        is.close();
        os.close();
        socket.close();
        System.out.println(rpcResponse.getRequestId());
    }
}
