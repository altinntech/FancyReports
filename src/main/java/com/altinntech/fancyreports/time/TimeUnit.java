package com.altinntech.fancyreports.time;

import lombok.Getter;

@Getter
public class TimeUnit {

    long ns;
    double ms;

    public TimeUnit(long ns) {
        set(ns);
    }

    public void set(long ns) {
        this.ns = ns;
        this.ms = (double) ns / 1_000_000.0;
    }

    public String getNsAsString() {
        return ns + " ns";
    }

    public String getMsAsString() {
        return ms + " ms";
    }
}
