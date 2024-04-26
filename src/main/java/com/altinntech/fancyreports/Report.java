package com.altinntech.fancyreports;

import com.altinntech.fancyreports.time.TimeTest;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.altinntech.fancyreports.ConsoleColors.*;

@EqualsAndHashCode
public class Report {

    private final String name;
    private final LocalDateTime creationDate;
    private final List<String> rows;
    private final ReportType reportType;

    private Report(String name, List<String> rows, ReportType type) {
        this.rows = rows;
        this.reportType = type;
        this.creationDate = LocalDateTime.now();
        this.name = name;
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----=== ").append(YELLOW).append(name).append(RESET).append(" ===-----");
        sb.append("\n");
        sb.append(PURPLE + "Date: ").append(creationDate).append(RESET);
        sb.append("\n\n");
        for (String row : rows) {
            sb.append(" - ").append(row).append("\n");
        }
        return sb.toString();
    }

    public String getTinyText() {
        StringBuilder sb = new StringBuilder();
        for (String row : rows) {
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    public void print() {
        if (this.reportType == ReportType.FULL) {
            System.out.println(getText());
        } else {
            System.out.println(getTinyText());
        }
    }

    public static class ReportBuilder {

        protected String name;
        protected List<String> rows;

        private ReportBuilder() {
            super();
            this.rows = new ArrayList<>();
        }

        public ReportBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ReportBuilder addRow(String row) {
            this.rows.add(row);
            return this;
        }

        public ReportBuilder addRecord(Record record) {
            this.rows.add(record.toString());
            return this;
        }

        public ReportBuilder addRecord(String row, Object value) {
            this.rows.add(new Record(row, value).toString());
            return this;
        }

        public ReportBuilder addTimeStatistics(TimeTest timeTest) {
            this.rows.add(new Record("Elapsed time", timeTest.getElapsedTime().getMsAsString()).toString());
            if (timeTest.getExtension() != null) {
                this.rows.add(new Record("Avg time", timeTest.getExtension().getAvgTimeElapsed().getMsAsString()).toString());
                this.rows.add(new Record("Best time", timeTest.getExtension().getBestTimeElapsed().getMsAsString()).toString());
                this.rows.add(new Record("Worst time", timeTest.getExtension().getWorstTimeElapsed().getMsAsString()).toString());
            }
            return this;
        }

        public Report build() {
            return new Report(name, rows, ReportType.FULL);
        }

        public Report tinyBuild() {
            return new Report(name, rows, ReportType.TINY);
        }
    }

    enum ReportType {
        FULL,
        TINY,
        ;
    }
}
