package com.company.events;

public class Tick implements Comparable<Tick> {

    private final long number;
    private final Type type;

    public Tick(long number, Type type) {
        this.number = number;
        this.type = type;
    }
    public long getNumber() {
        return number;
    }
    public Type getType() {
        return type;
    }
    public enum Type {
        TickNote,
        TickTempoChange,
        TickTimeSignature
    }
    @Override
    public int compareTo(Tick tempTick) {
        if(tempTick.number <= number) {
            return 1;
        }
        return -1;
    }
}