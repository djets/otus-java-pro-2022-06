package dynamicProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
public class Ioc {
    private Ioc() {
    }

    static TestLoggingInterface createMyClass(TestLoggingInterface myClass) {
        Map<Method, Annotation> contextMethodListAnnotated = new HashMap<>();
        return (TestLoggingInterface) Proxy.newProxyInstance(myClass.getClass().getClassLoader(),
                new Class[]{TestLoggingInterface.class}, (Object proxy, Method method, Object[] args) -> {
                    if (contextMethodListAnnotated.containsKey(method)) {
                        System.out.printf("executed method from context: %s, param: ", method.getName());
                    } else {
                        if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(LogScam.class)) {
                            contextMethodListAnnotated.putIfAbsent(method, method.getAnnotation(LogScam.class));
                            System.out.printf("executed method: %s, param: ", method.getName());
                        }
                    }
                    return method.invoke(myClass, args);
                });
    }
}
