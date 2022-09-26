package dynamicProxy;

public class Demo {
    public void action() {
        TestLoggingInterface testClass = Ioc.createMyClass(new TestLogging());
        testClass.calculation(9);
        testClass.calculation(6, 7, "set");

    }

    public static void main(String[] args) {
        new Demo().action();
    }
}
