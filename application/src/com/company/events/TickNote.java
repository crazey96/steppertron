package com.company.events;

public class TickNote extends Tick {

    private final int generalNote;
    private final int note;
    private final int octave;
    private final int velocity;
    private final int channel;
    private final boolean on;
    private final int track;

    public TickNote(long number, Type type, int generalNote, int note, int octave, int velocity, int channel, boolean on, int track) {
        super(number, type);
        this.generalNote = generalNote;
        this.note = note;
        this.octave = octave;
        this.velocity = velocity;
        this.channel = channel;
        this.on = on;
        this.track = track;
    }
    public int getGeneralNote() {
        return generalNote;
    }
    public int getNote() {
        return note;
    }
    public int getOctave() {
        return octave;
    }
    public int getVelocity() {
        return velocity;
    }
    public int getChannel() {
        return channel;
    }
    public boolean getOn() {
        return on;
    }
    public int getTrack() {
        return track;
    }
}