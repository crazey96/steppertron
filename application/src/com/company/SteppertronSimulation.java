package com.company;

import com.company.events.Tick;

import javax.sound.midi.*;
import java.util.ArrayList;

public class SteppertronSimulation extends RuntimePlatform {

    private final MidiChannel[] midiChannels;

    public SteppertronSimulation(Sequence sequence, ArrayList<Tick> ticks) throws MidiUnavailableException {
        super(sequence, ticks);
        // initialize synthesizer and channels
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        this.midiChannels = synthesizer.getChannels();
    }
    @Override
    public void playNote(int note, boolean on, int track) {
        playNote(note, on);
    }
    private void playNote(int note, boolean on) {
        if(on) {
            midiChannels[0].noteOn(note, 200);
        } else {
            midiChannels[0].noteOff(note);
        }
    }
}