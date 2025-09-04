package learning.itstep.java.newpackage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Chaining {

    private long t;

    public void demo() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        t = System.nanoTime();
        Future<?> task1 = CompletableFuture
                .supplyAsync(stringSupplier, threadPool)
                .thenApply(processor1)
                .thenApply(processor2)
                .thenAccept(printer);
        
        try {
            task1.get();
            System.out.printf(
                    "%.1f ms: chain finish\n",
                    (System.nanoTime() - t) / 1e6);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            threadPool.shutdown();
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
            threadPool.shutdownNow();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private final Supplier<String> stringSupplier = new Supplier<String>() {
        @Override
        public String get() {
            System.out.printf("%.1f ms: StringSupplier start\n",
                    (System.nanoTime() - t) / 1e6);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.printf("%.1f ms: StringSupplier finish\n",
                    (System.nanoTime() - t) / 1e6);
            return "Origin 1";
        }
    };

    private final Supplier<String> stringSupplier2 = () -> {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return "Origin 2";
    };

    private final Function<String, String> processor1 = new Function<String, String>() {
        @Override
        public String apply(String input) {
            System.out.printf("%.1f ms: processor1 start\n",
                    (System.nanoTime() - t) / 1e6);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.printf("%.1f ms: processor1 finish\n",
                    (System.nanoTime() - t) / 1e6);
            return input + ". Processed 1";
        }
    };

    private final Function<String, String> processor2 = input -> {
        System.out.printf("%.1f ms: processor2 start\n",
                (System.nanoTime() - t) / 1e6);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.printf("%.1f ms: processor2 finish\n",
                (System.nanoTime() - t) / 1e6);
        return input + ". Processed 2";
    };

    private final Consumer<String> printer = new Consumer<String>() {
        @Override
        public void accept(String input) {
            System.out.printf("%.1f ms: Consumer(printer) got result '%s'\n",
                    (System.nanoTime() - t) / 1e6, input);
        }
    };
}
