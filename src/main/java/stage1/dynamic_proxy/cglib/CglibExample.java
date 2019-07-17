package stage1.dynamic_proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import stage1.dynamic_proxy.service.BookApi;
import stage1.dynamic_proxy.service.impl.BookApiImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class CglibExample {

    public static void main(String [] agrs){
        BookApi bookApi = createCglibProxy(new BookApiImpl());
        bookApi.sell();
    }

    private static BookApi createCglibProxy(BookApi bookApi){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibInterceptor(bookApi));
        enhancer.setInterfaces(new Class[]{BookApi.class});
        return (BookApi)enhancer.create();
    }


    private static class CglibInterceptor implements MethodInterceptor{

        Object object;

        CglibInterceptor(Object object){
            this.object = object;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("cglib .. ");

            return methodProxy.invoke(object, objects);
        }
    }
}
