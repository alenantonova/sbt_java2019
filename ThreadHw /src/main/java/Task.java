import java.util.concurrent.Callable;

public class Task<T> {
    private T result;
    boolean done;
    boolean executing;
    private Callable<? extends T> task;
    private MyTaskException e;


    public Task(Callable<? extends T> callable) {
        this.task = callable;
        done = false;
        executing = false;

    }

    public T get() throws MyTaskException {
        if (done) {
            return result;
        }
        if (e != null) {
            throw e;
        }

        synchronized (this) {
            if (!done && e == null) {
                try {
                    result = task.call();
                } catch (Exception e1) {
                    e = new MyTaskException();
                    throw e;
                }
                done = true;
                return result;
            }

            if (done) {
                return result;
            } else {
                throw e;
            }
        }
    }
}
