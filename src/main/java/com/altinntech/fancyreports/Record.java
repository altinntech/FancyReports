package com.altinntech.fancyreports;

import lombok.Data;

@Data
public class Record {

    private String name;
    private String value;

    public Record(String name, Object value) {
        this.name = ConsoleColors.GREEN + ConsoleColors.BOLD + name + ConsoleColors.RESET;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
