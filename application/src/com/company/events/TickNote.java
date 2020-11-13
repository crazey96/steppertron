package com.company.events;

public class TickNote extends Tick {

    private final int note;
    private final int octave;
    private final int channel;
    private final boolean on;

    public TickNote(long number, Type type, int note, int octave, int channel, boolean on) {
        super(number, type);
        this.note = note;
        this.octave = octave;
        this.channel = channel;
        this.on = on;
    }
    public int getNote() {
        return note;
    }
    public int getOctave() {
        return octave;
    }
    public int getChannel() {
        return channel;
    }
    public boolean getOn() {
        return on;
    }
}