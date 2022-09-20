package dynamicProxy;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Ioc {

    public Ioc() {
    }

    static TestLoggingInterface createMyClass(TestLoggingInterface myClass) {
        return (TestLoggingInterface) Proxy.newProxyInstance(myClass.getClass().getClassLoader(),
                new Class[]{TestLoggingInterface.class}, (Object proxy, Method method, Object[] args) -> {
                    if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(LogScam.class)) {
                        System.out.printf("executed method: %s, param: ", method.getName());
                    }
                    return method.invoke(myClass, args);
                });
    }


}
