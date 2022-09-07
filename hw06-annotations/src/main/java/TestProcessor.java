import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestProcessor {
    private final String nameTestClass;

    private final List<Method> methodsMarkedAnnotatedBefore = new ArrayList<>();
    private final List<Method> methodsMarkedAnnotatedTest = new ArrayList<>();
    private final List<Method> methodsMarkedAnnotatedAfter = new ArrayList<>();

    public TestProcessor(String nameTestClass) {
        this.nameTestClass = nameTestClass;
    }

    private Object instantiation(String nameTestClass) {
        try {
            Class<?> clazz = Class.forName(nameTestClass);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Class not found");
        }
    }

    private void getDeclaredMethodMarkedAnnotations() {
        var methodsMarkedAnnotations = Arrays.stream(instantiation(nameTestClass).getClass()
                .getDeclaredMethods()).filter(method -> method.getDeclaredAnnotations().length > 0).toList();
        for (Method method : methodsMarkedAnnotations) {
            if (method.isAnnotationPresent(Before.class)) methodsMarkedAnnotatedBefore.add(method);
            if (method.isAnnotationPresent(Test.class)) methodsMarkedAnnotatedTest.add(method);
            if (method.isAnnotationPresent(After.class)) methodsMarkedAnnotatedAfter.add(method);
        }
    }

    private void executeOfMethodAnnotatedBeforeAndAfter(String annotation, Object instanceTestClass) {
        List<Method> getListMethodsMarkedAnnotated = new ArrayList<>();
        switch (annotation) {
            case "Before" -> getListMethodsMarkedAnnotated = methodsMarkedAnnotatedBefore;
            case "After" -> getListMethodsMarkedAnnotated = methodsMarkedAnnotatedAfter;
        }

        getListMethodsMarkedAnnotated.forEach(method -> {
            try {
                method.setAccessible(true);
                method.invoke(instanceTestClass);
            } catch (IllegalAccessException | InvocationTargetException | RuntimeException e) {
                throw new RuntimeException("Fail: method " + method.getName());
            }
        });
    }

    void executeOfMethodAnnotatedTest() {
        int tests = 0;
        int testPassed = 0;
        getDeclaredMethodMarkedAnnotations();

        for (Method methodWithTestAnnotation : methodsMarkedAnnotatedTest) {
            tests++;
            Object instanceTestClass = instantiation(nameTestClass);
            try {
                executeOfMethodAnnotatedBeforeAndAfter("Before", instanceTestClass);
                methodWithTestAnnotation.setAccessible(true);
                methodWithTestAnnotation.invoke(instanceTestClass);
                System.out.printf("Running Test. Method - %s%n", methodWithTestAnnotation.getName());
                testPassed++;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            } catch (InvocationTargetException | IllegalAccessException e) {
                System.out.println(methodWithTestAnnotation + " failed: " + e.getCause());
            } finally {
                executeOfMethodAnnotatedBeforeAndAfter("After", instanceTestClass);
            }
        }

        System.out.printf("Passed: %d, Failed: %d, All: %d%n", testPassed, tests - testPassed, tests);
    }
}
