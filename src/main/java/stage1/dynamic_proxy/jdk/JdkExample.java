package stage1.dynamic_proxy.jdk;

import stage1.dynamic_proxy.service.BookApi;
import stage1.dynamic_proxy.service.impl.BookApiImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkExample {
    public static void main(String [] agrs){
        BookApi bookApi = createJdkProxy(new BookApiImpl());
        bookApi.sell();
    }

    private static BookApi createJdkProxy(BookApi bookApi){
        return (BookApi) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{BookApi.class}, new JdkHandler(bookApi));
    }

    private static class JdkHandler implements InvocationHandler {

        Object object;

        JdkHandler(Object object){
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

            System.out.println("proxy..");

            return method.invoke(object,args);
        }
    }
}
