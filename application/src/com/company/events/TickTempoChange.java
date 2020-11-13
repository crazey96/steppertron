package com.company.events;

public class TickTempoChange extends Tick {

    private final int tempo;

    public TickTempoChange(long number, Tick.Type type, int tempo) {
        super(number, type);
        this.tempo = tempo;
    }
    public int getTempo() {
        return tempo;
    }
}