package utils.executiondata;

public class TestExecutionData {
    private String testName;
    private long executionTime; // in milliseconds
    private String status; // e.g., "PASSED", "FAILED"

    private int totalRuntime;
    // Constructors, getters, and setters

    //TODO Only PASSED Test Cases get into the execution_data.txt
    public TestExecutionData(String testName, long executionTime, String status) {
        this.testName = testName;
        this.executionTime = executionTime;
        this.status = status;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalRuntime(){
        return this.totalRuntime;
    }

    public void setTotalRuntime(int totalRuntime){
        this.totalRuntime = totalRuntime;
    }
}
