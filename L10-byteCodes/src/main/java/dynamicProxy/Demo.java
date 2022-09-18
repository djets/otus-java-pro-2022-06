package dynamicProxy;

public class Demo {
    public void action() {
        TestLoggingInterface testMyClass = Ioc.createMyClass();
        testMyClass.calculation(6);
//        testMyClass.logMethodParam();
    }

    public static void main(String[] args) {
        new Demo().action();
    }
}
