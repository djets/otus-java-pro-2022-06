package dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Ioc {
    private Ioc() {
    }
    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
//        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
//                new Class<?>[]{TestLoggingInterface.class}, handler);

        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                ((proxy, method, args) -> {
                    if(!method.isAnnotationPresent(LogScam.class)){

                        Arrays.stream(method.getParameters());
                        System.out.println(method.getName() + " " + method.getModifiers());
                    } else {
                        System.out.println(method.getName() + " skip");
                    }
                    return proxy;
                })
                );
    }
        static class DemoInvocationHandler implements InvocationHandler {
            private final TestLoggingInterface myClass;

            DemoInvocationHandler(TestLoggingInterface myClass) {
                this.myClass = myClass;
            }

            private void getDeclaredMethodMarkedAnnotations() throws ClassNotFoundException {
                Map<Method, Object[]> methodsMarkedAnnotatedLogScam = new HashMap<>();
                for (Method method : myClass.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(LogScam.class)) {
                        methodsMarkedAnnotatedLogScam.put(method, method.getParameters());
                    }
                }
            }



            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("invoking method:" + method);
                return method.invoke(myClass, args);
            }

            @Override
            public String toString() {
                return "DemoInvocationHandler{" +
                        "myClass=" + myClass +
                        '}';
            }
        }

    }
