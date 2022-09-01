public class SomeTestClass {
    @Before
    public String methodOne(){
        return "Setup before running test";
    }

    public static void methodTwo() {}
    @Test
    private static void methodTree() {
        throw new RuntimeException("Exception throw");
    }
    @Test
    public static void methodFour() {}
    @Test
    protected void methodFive() {}
    @After
    private String methodSix() {
        return "The test is completed";
    }
}
