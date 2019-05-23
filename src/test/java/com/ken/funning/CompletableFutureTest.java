package com.ken.funning;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * ref: https://www.jdon.com/50027
 */
public class CompletableFutureTest {

    // 简单用法
    @Test
    public void testSimple() {

        System.out.println("Start");
        CompletableFuture.supplyAsync(this::step1);
        System.out.println("End");
        while (true) {
        }
    }

    // 回调(step1 完成后调用 step2)
    @Test
    public void testAccept() {
        System.out.println("Start");
        CompletableFuture.supplyAsync(this::step1)
                .thenAccept(this::step2);
        System.out.println("End");
        while (true) {
        }
    }


    // 多个回调(step1 完成后调用 step2 完成后再调用 step4)
    @Test
    public void testApplyAccept() {
        System.out.println("Start");
        CompletableFuture.supplyAsync(this::step1).
                thenApply(this::step2).
                thenAccept(this::step4);
        System.out.println("End");
        while (true) {
        }
    }

    private String step1() {
        System.out.println("Step1 start");

        try {
            System.out.println("Step1 sleep 5 seconds start.");
            Thread.sleep(5 * 1000);
            System.out.println("Step1 sleep 5 seconds end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Step1 end");
        return "step1";
    }

    private String step2(String param) {
        System.out.println("Step2 start, param:" + param);

        try {
            System.out.println("Step2 sleep 5 seconds start.");
            Thread.sleep(3 * 1000);
            System.out.println("Step2 sleep 5 seconds end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Step2 end");
        return "step2";
    }

    private String step3() {
        System.out.println("Step3 start");

        try {
            System.out.println("Step3 sleep 4 seconds start.");
            Thread.sleep(4 * 1000);
            System.out.println("Step3 sleep 4 seconds end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Step3 end");
        return "step3";
    }

    private String step4(String param) {
        System.out.println("Step4 start, param:" + param);

        try {
            System.out.println("Step4 sleep 3 seconds start.");
            Thread.sleep(3 * 1000);
            System.out.println("Step4 sleep 3 seconds end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Step4 end");
        return "step4";
    }
}
