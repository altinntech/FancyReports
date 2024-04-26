package com.altinntech.fancyreports.time;

import lombok.Getter;

@Getter
public class TimeTestExtension {

    int timeTestIterations;
    TimeUnit avgTimeElapsed;
    final TimeUnit bestTimeElapsed;
    final TimeUnit worstTimeElapsed;

    public TimeTestExtension() {
        bestTimeElapsed = new TimeUnit(Long.MAX_VALUE);
        worstTimeElapsed = new TimeUnit(-1L);
    }

    public TimeTestExtension(int timeTestIterations) {
        bestTimeElapsed = new TimeUnit(Long.MAX_VALUE);
        worstTimeElapsed = new TimeUnit(-1L);
        this.timeTestIterations = timeTestIterations;
    }

    protected void computeAll(TimeTest base) {
        avgTimeElapsed = new TimeUnit(base.elapsedTime.ns / timeTestIterations);
    }

    public void setTimeTestIterations(int timeTestIterations) {
        this.timeTestIterations = timeTestIterations;
    }

    public void capture(TimeTest base) {
        if (base.captureDelta < bestTimeElapsed.getNs()) {
            bestTimeElapsed.set(base.captureDelta);
        }
        if (base.captureDelta > worstTimeElapsed.getNs()) {
            worstTimeElapsed.set(base.captureDelta);
        }
    }
}
