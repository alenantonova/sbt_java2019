import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class MyExecutionManager implements ExecutionManager {
    private int threads_num;
    private int tasks_num;
    private int completed;
    private int failed;
    private int interrupted;
    private  Managed_Worker[] threads;
    private LinkedBlockingQueue<Runnable> tasks;
    private Runnable callback;

    MyExecutionManager(int threads_num) {
        this.threads_num = threads_num;
        this.failed = 0;
        this.completed = 0;
        this.interrupted = 0;
        this.tasks_num = 0;
        this.threads = new Managed_Worker[threads_num];
        this.tasks = null;
        this.callback = null;

    }

    public Context execute(Runnable callback, Runnable... tasks) {
        this.tasks = (LinkedBlockingQueue<Runnable>) Arrays.asList(tasks);

        for (int i = 0; i < threads_num; i++) {
            threads[i] = new Managed_Worker();
        }
        Context result = new MyContext(completed, failed, interrupted);

        for (int i = 0; i < threads_num; i++) {
            threads[i].start();
        }
        if (interrupted == 0) {
            callback.run();
        }

        return result;
    }

    public class Managed_Worker extends Thread {
        public void run() {
            Runnable cur_task;

            while(true) {
                synchronized (tasks) {
                    if (tasks.isEmpty()) {
                        return;
                    }
                    cur_task = tasks.poll();
                }


                try {
                    cur_task.run();
                    completed += 1;
                } catch (RuntimeException e) {
                    failed += 1;
                    e.printStackTrace();
                }
            }
        }
    }
}
