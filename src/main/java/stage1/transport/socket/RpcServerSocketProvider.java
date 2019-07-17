package stage1.transport.socket;

import stage1.serialization.Serialization;
import stage1.serialization.impl.Hessian2Serialization;
import stage1.transport.model.RpcRequest;
import stage1.transport.model.RpcResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServerSocketProvider {

    public static void main(String[] args) throws IOException {
        Serialization serialization = new Hessian2Serialization();

        ServerSocket serverSocket = new ServerSocket(8888);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while(true){
            final Socket socket = serverSocket.accept();
            executorService.execute(()->{
                try{
                    InputStream is = socket.getInputStream();
                    OutputStream os = socket.getOutputStream();

                    try{
                        DataInputStream dis = new DataInputStream(is);
                        int length = dis.readInt();
                        byte[] requestBody = new byte[length];
                        dis.read(requestBody);

                        RpcRequest rpcRequest = serialization.deserialize(requestBody, RpcRequest.class);

                        RpcResponse rpcResponse = invoke(rpcRequest);

                        byte[] responseBody = serialization.serialize(rpcResponse);

                        DataOutputStream dos = new DataOutputStream(os);
                        dos.writeInt(requestBody.length);
                        dos.write(responseBody);
                        dos.flush();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        is.close();
                        os.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try{
                        socket.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static RpcResponse invoke(RpcRequest rpcRequest){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        return rpcResponse;
    }
}
