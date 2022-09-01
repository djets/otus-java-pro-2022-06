import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Runner {

    public static void main(String[] args) {
        var instanceSomeTestClass = instantiation("SomeTestClass");
        runTest(instanceSomeTestClass);
    }

    private static Object instantiation(String nameTestClass) {
        try {
            Class<?> clazz = Class.forName(nameTestClass);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Class not found");
        }
    }

    private static void runBefore(Object someTestClass) {
        for (Method method : someTestClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    method.setAccessible(true);
                    System.out.println(method.invoke(someTestClass));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void runTest(Object someTestClass) {
        int tests = 0;
        int testPassed = 0;

        for (Method method : someTestClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    runBefore(someTestClass);
                    method.setAccessible(true);
                    method.invoke(someTestClass);
                    System.out.printf("Running Test. Method - %s%n", method.getName());
                    testPassed++;
                    runAfter(someTestClass);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println(method + " failed: " + e.getCause());
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", testPassed, tests - testPassed);
    }

    private static void runAfter(Object someTestClass) {
        for (Method method : someTestClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    method.setAccessible(true);
                    System.out.println(method.invoke(someTestClass));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
