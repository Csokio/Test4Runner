package maytestuse.probetestsuite;

//import org.junit.platform.launcher.LauncherDiscoveryRequest;
//import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
//import org.junit.platform.launcher.listeners.TestExecutionSummary;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DummyMain {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(QAShellGBTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Successful: " + result.wasSuccessful());
    }
}
