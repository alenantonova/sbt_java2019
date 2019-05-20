import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool {
    private final int min_threads_num;
    private final int max_threads_num;
    private final Worker[] threads;
    private final ArrayList<Worker> extra_threads;
    private final LinkedBlockingQueue<Runnable> tasks;


    ScalableThreadPool(int min_threads_num, int max_threads_num) {
        this.min_threads_num = min_threads_num;
        this.max_threads_num = max_threads_num;

        threads = new Worker[min_threads_num];
        extra_threads = new ArrayList<Worker>();
        tasks = new LinkedBlockingQueue<Runnable>();
    }

    public void start() {
        for (int i = 0; i < min_threads_num; i++) {
            threads[i] = new Worker();
            threads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        for (Worker worker: extra_threads) {
            if (!worker.isAlive()) {
                extra_threads.remove(worker);
            }
        }
        synchronized (tasks) {
            if (!tasks.isEmpty() &&
                    extra_threads.size() < (max_threads_num - min_threads_num)) {
                Worker extra_worker = new Worker();
                extra_worker.start();
                extra_threads.add(extra_worker);

            }
            tasks.add(runnable);
            tasks.notify();
        }
    }

    private class Worker extends Thread {
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

