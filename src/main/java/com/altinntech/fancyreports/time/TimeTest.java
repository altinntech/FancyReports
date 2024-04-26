package com.altinntech.fancyreports.time;

import lombok.Getter;

@Getter
public class TimeTest {

    long start;
    long end;
    long lastCapture;
    long captureDelta;
    TimeUnit elapsedTime;
    TimeTestExtension extension;

    long latency;

    private TimeTest() {
    }

    public static TimeTest start(TimeTestExtension extension) {
        TimeTest timeTest = start();
        timeTest.extension = extension;
        return timeTest;
    }

    public static TimeTest start() {
        TimeTest test = new TimeTest();
        test.calibrate();
        test.start = System.nanoTime();
        test.lastCapture = test.start;
        return test;
    }

    public static TimeTest startWithoutCalibration() {
        TimeTest test = new TimeTest();
        test.start = System.nanoTime();
        test.lastCapture = test.start;
        return test;
    }

    public TimeTest stop() {
        this.end = System.nanoTime() - latency;
        this.elapsedTime = new TimeUnit(end - start);
        if (extension != null) {
            extension.computeAll(this);
        }
        return this;
    }

    public void capture() {
        long now  = System.nanoTime();
        captureDelta = now - lastCapture;
        lastCapture = now;
        if (extension != null) {
            extension.capture(this);
        }
    }

    private void calibrate() {
        long sumTime = 0L;
        for (int i = 0; i < 100; i++) {
            TimeTest calibrateTimer = startWithoutCalibration();
            calibrateTimer.stop();
            sumTime += calibrateTimer.getElapsedTime().getNs();
        }
        this.latency = sumTime / 100;
    }
}
