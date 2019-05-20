import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final int threads_num;
    private final Worker[] threads;
    private final LinkedBlockingQueue<Runnable> tasks;


    FixedThreadPool(int threads_num) {
        this.threads_num = threads_num;

        threads = new Worker[threads_num];
        tasks = new LinkedBlockingQueue<Runnable>();
    }

    public void start() {
        for (int i = 0; i < threads_num; i++) {
            threads[i] = new Worker();
            threads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notify();
        }
    }

    public class Worker extends Thread {
        public void run() {
            Runnable cur_task;

            while(true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    cur_task = tasks.poll();
                }

                try {
                    cur_task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
