public class SomeTestClass {
    @Before
    public void methodOne() {
        System.out.println("Setup one \"@Before\" running test");
    }

    //    @Before
    public static void methodTwo() {
//        System.out.println("Setup two \"@Before\" running test");
//        throw new RuntimeException("Setup two \"@Before\" fail");
    }

    @Test
    private static void methodTree() {
        throw new RuntimeException("Exception throw");
    }

    @Test
    public static void methodFour() {
    }

    @Test
    protected void methodFive() {
    }

    @After
    private void methodSix() {
        System.out.println("The test is finished");
    }
}
