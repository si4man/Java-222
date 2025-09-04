package learning.itstep.java.newpackage;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Chaining2 {

    private long t;
    private final Random random = new Random();
    private final Locale UA = Locale.forLanguageTag("uk-UA");

    public void demo() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        t = System.nanoTime();
        Future<?> task = CompletableFuture
                .supplyAsync(randomInt, threadPool)
                .thenApply(signAnalyzer)
                .thenAccept(printer);
        try {
            task.get();
            threadPool.shutdown();
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
            threadPool.shutdownNow();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private final Supplier<Integer> randomInt = () -> {
        printWithTime("Supplier start");
        sleep();
        int n = random.nextInt();
        printWithTime("Supplier finish WITH RESULT " + n);
        return n;
    };

    private final Function<Integer, String> signAnalyzer = num -> {
        printWithTime("signAnalyzer start WITH INPUT " + num);
        sleep();
        String status = (num > 0) ? "positive" : (num < 0) ? "negative" : "zero";
        String msg = String.format("Number %d %s", num, status);
        printWithTime("signAnalyzer finish WITH RESULT '" + msg + "'");
        return msg;
    };

    private final Consumer<String> printer = str -> {
        printWithTime("printer start");
        sleep();
        printWithTime("printer finish with result: " + str);
    };

    private void printWithTime(String message) {
        System.out.printf(UA, "%.1f ms: %s%n", time(), message);
    }

    private double time() {
        return (System.nanoTime() - t) / 1e6;
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(100, 500));
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
