package stage1.simple_rpc.test;

import stage1.simple_rpc.framework.RpcFramework;

public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}