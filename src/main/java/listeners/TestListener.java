package listeners;

import commonutils.ExtentManager;
import commonutils.ExtentTestManager;
import commonutils.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Enterprise TestNG Listener
 *
 * Handles TestNG execution events.
 */
public class TestListener implements ITestListener {

    private static final Logger LOGGER =
            LoggerManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {

        LOGGER.info("==============================================");
        LOGGER.info("Test Suite Started : {}", context.getName());
        LOGGER.info("==============================================");

    }

    @Override
    public void onTestStart(ITestResult result) {

        LOGGER.info("Test Started : {}",
                result.getMethod().getMethodName());

        ExtentTestManager.info(
                "Test Started : " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        LOGGER.info("Test Passed : {}",
                result.getMethod().getMethodName());

        ExtentTestManager.pass("Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        LOGGER.error("Test Failed : {}",
                result.getMethod().getMethodName());

        LOGGER.error(result.getThrowable());

        if (result.getThrowable() != null) {
            ExtentTestManager.fail(
                    result.getThrowable().getMessage());
        } else {
            ExtentTestManager.fail("Test Failed");
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        LOGGER.warn("Test Skipped : {}",
                result.getMethod().getMethodName());

        ExtentTestManager.warning("Test Skipped");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(
            ITestResult result) {

        // Not Used

    }

    @Override
    public void onFinish(ITestContext context) {

        LOGGER.info("==============================================");
        LOGGER.info("Test Suite Finished : {}", context.getName());
        LOGGER.info("==============================================");

        ExtentManager.flush();

    }

}