package com.altinntech;

import com.altinntech.fancyreports.Report;
import com.altinntech.fancyreports.time.TimeTest;
import com.altinntech.fancyreports.time.TimeTestExtension;
import com.altinntech.fancyreports.time.TimeUnit;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    public void testReport() {
        Report.builder()
                .name("someTest")
                .addRecord("Some test response", 10)
                .addRecord("Another test response", 12)
                .build().print();
    }

    public void testTimerReport() throws InterruptedException {
        TimeTest timer = TimeTest.start();
        Thread.sleep(2000); // some long operation
        TimeUnit executionTime = timer.stop().getElapsedTime();

        Report.builder()
                .name("timerTest")
                .addRecord("Time for operation", executionTime.getMsAsString())
                .build().print();

        assertTrue(Math.abs(2000.0 - executionTime.getMs()) < 0.5);
    }

    public void testAdvancedTimerReport() throws InterruptedException {
        int iterations = 100;

        TimeTestExtension extension = new TimeTestExtension(iterations);
        TimeTest timer = TimeTest.start(extension);
        for (int i = 0; i < iterations; i++) {
            Thread.sleep(10);
            timer.capture();
        }
        TimeUnit executionTime = timer.stop().getElapsedTime();

        Report.builder()
                .name("advancedTimerTest")
                .addRecord("Time for operation", executionTime.getMsAsString())
                .addRecord("Avg time for iteration", timer.getExtension().getAvgTimeElapsed().getMsAsString())
                .addRecord("Best time for iteration", timer.getExtension().getBestTimeElapsed().getMsAsString())
                .addRecord("Worst time for iteration", timer.getExtension().getWorstTimeElapsed().getMsAsString())
                .build().print();

        assertTrue(Math.abs(10.0 - timer.getExtension().getAvgTimeElapsed().getMs()) < 0.5);
    }
}
