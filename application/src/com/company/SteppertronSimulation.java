package com.company;

import com.company.events.Tick;

import javax.sound.midi.*;
import java.util.ArrayList;

public class SteppertronSimulation extends RuntimePlatform {

    private MidiChannel[] midiChannels;

    public SteppertronSimulation(Sequence sequence, ArrayList<Tick> ticks) {
        super(sequence, ticks);
        // initialize synthesizer and channels
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            this.midiChannels = synthesizer.getChannels();
        } catch (MidiUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    protected void noteAction(String note, int numericalNote, boolean on) {
        if(midiChannels == null) {
            return;
        }
        if(on) {
            midiChannels[0].noteOn(numericalNote, 100);
        } else {
            midiChannels[0].noteOff(numericalNote);
        }
    }
}