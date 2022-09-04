import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
        String nameTestClass = "SomeTestClass";
        runTest(getDeclaredMethodMarkedAnnotations(nameTestClass), nameTestClass);
    }

    private static Object instantiation(String nameTestClass) {
        try {
            Class<?> clazz = Class.forName(nameTestClass);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Class not found");
        }
    }

    private static List<Method> getDeclaredMethodMarkedAnnotations(String nameTestClass) {
        try {
            return Arrays.stream(Class.forName(nameTestClass).getDeclaredMethods())
                    .filter(method -> method.getDeclaredAnnotations().length > 0).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found");
        }
    }

    private static void runBefore(List<Method> getDeclaredMethodMarkedAnnotations, Object instanceTestClass) {
        getDeclaredMethodMarkedAnnotations.stream().filter(method -> method.isAnnotationPresent(Before.class))
                .forEach(method -> {
                    try {
                        method.setAccessible(true);
                        System.out.println(method.invoke(instanceTestClass));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getCause());
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    } finally {
                        runAfter(getDeclaredMethodMarkedAnnotations, instanceTestClass);
                    }
                });
    }

    private static void runTest(List<Method> getDeclaredMethodMarkedAnnotations, String nameTestClass) {
        int tests = 0;
        int testPassed = 0;

        for (Method method : getDeclaredMethodMarkedAnnotations
                .stream().filter(method -> method.isAnnotationPresent(Test.class))
                .collect(Collectors.toList())) {
            tests++;
            try {
                Object instanceTestClass = instantiation(nameTestClass);
                runBefore(getDeclaredMethodMarkedAnnotations, instanceTestClass);
                method.setAccessible(true);
                method.invoke(instanceTestClass);
                System.out.printf("Running Test. Method - %s%n", method.getName());
                testPassed++;
            } catch (RuntimeException | InvocationTargetException | IllegalAccessException e) {
                System.out.println(method + " failed: " + e.getCause());
            }
        }

        System.out.printf("Passed: %d, Failed: %d%n", testPassed, tests - testPassed);
    }

    private static void runAfter(List<Method> getDeclaredMethodMarkedAnnotations, Object instanceTestClass) {
        getDeclaredMethodMarkedAnnotations.stream().filter(method -> method.isAnnotationPresent(After.class))
                .forEach(method -> {
                    try {
                        method.setAccessible(true);
                        System.out.println(method.invoke(instanceTestClass));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getCause());
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    }
                });
    }
}
