public class MyContext implements Context {
    private int completed;
    private int failed;
    private int interrupted;

    MyContext (int completed, int failed, int interrupted) {
        this.completed = completed;
        this.failed = failed;
        this.interrupted = interrupted;
    }

    public int getCompletedTaskCount() {
        return completed;
    }

    public int getFailedTaskCount() {
        return failed;
    }

    public int getInterruptedTaskCount() {
        return interrupted;
    }

    public void interrupt() {
    }

    public boolean isFinished() {
        return true;
    }

}