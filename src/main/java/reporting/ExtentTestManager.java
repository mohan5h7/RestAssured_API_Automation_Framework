package reporting;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    /**
     * Start Test with Test Name
     */
    public static void startTest(String testName) {

        startTest(testName, "");

    }

    /**
     * Start Test with Test Name and Description
     */
    public static void startTest(String testName,
                                 String description) {

        ExtentTest test = ExtentManager.getInstance()
                .createTest(testName, description);

        TEST.set(test);

    }

    /**
     * Get Current Test
     */
    public static ExtentTest getTest() {

        return TEST.get();

    }

    /**
     * Remove Current Test
     */
    public static void endTest() {

        TEST.remove();

    }

    //================ Logging Methods ================//

    public static void info(String message) {

        if (TEST.get() != null) {
            TEST.get().info(message);
        }

    }

    public static void pass(String message) {

        if (TEST.get() != null) {
            TEST.get().pass(message);
        }

    }

    public static void fail(String message) {

        if (TEST.get() != null) {
            TEST.get().fail(message);
        }

    }

    public static void warning(String message) {

        if (TEST.get() != null) {
            TEST.get().warning(message);
        }

    }

}