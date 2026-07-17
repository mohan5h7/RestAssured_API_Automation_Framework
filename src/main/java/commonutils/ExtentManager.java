package commonutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

/**
 * Extent Report Manager
 *
 * Creates a single ExtentReports instance
 * for the entire framework.
 */
public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    /**
     * Returns singleton ExtentReports instance.
     */
    public static synchronized ExtentReports getInstance() {

        if (extent == null) {

            createInstance();

        }

        return extent;

    }

    /**
     * Creates Extent Report.
     */
    private static void createInstance() {

        String reportPath =
                System.getProperty("user.dir")
                        + File.separator
                        + "Reports"
                        + File.separator
                        + "API_Automation_Report.html";

        ExtentSparkReporter spark =
                new ExtentSparkReporter(reportPath);

        spark.config().setReportName(
                "API Automation Report");

        spark.config().setDocumentTitle(
                "Execution Report");

        extent = new ExtentReports();

        extent.attachReporter(spark);

        extent.setSystemInfo(
                "Framework",
                "Rest Assured");

        extent.setSystemInfo(
                "Language",
                "Java");

        extent.setSystemInfo(
                "Tester",
                System.getProperty("user.name"));

    }

    /**
     * Flush report.
     */
    public static void flush() {

        if (extent != null) {

            extent.flush();

        }

    }

}