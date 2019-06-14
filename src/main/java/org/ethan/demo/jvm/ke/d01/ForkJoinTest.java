package org.ethan.demo.jvm.ke.d01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = pool.submit(new SumTask(1L, 100000L));
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        long sum = 0;
//        for (long i = 0; i <= 100000L; i++) {
//            sum += i;
//        }
//        System.out.println(sum);
    }
}

class SumTask extends RecursiveTask<Long> {

    private static final long COMPUTE_COUNT = 500;
    private Long start;
    private Long end;

    public SumTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        Long a = end - start;
        if (a > COMPUTE_COUNT) {
            Long mid = a / 2;
            // 进行任务拆分
            SumTask rightTask = new SumTask(start, start + mid);
            SumTask leftTask = new SumTask(start + mid + 1, end);
            invokeAll(rightTask, leftTask);
            return rightTask.join() + leftTask.join();
        } else {
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
