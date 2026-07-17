package retry;

import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import commonutils.LoggerManager;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger LOGGER =
            LoggerManager.getLogger(RetryAnalyzer.class);

    private static final int MAX_RETRY_COUNT = 2;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < MAX_RETRY_COUNT) {

            retryCount++;

            LOGGER.info(
                    "Retrying Test : {} | Retry Attempt : {}",
                    result.getName(),
                    retryCount);

            return true;
        }

        LOGGER.info(
                "Retry limit reached for Test : {}",
                result.getName());

        return false;
    }
}