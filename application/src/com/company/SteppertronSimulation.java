package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;
import com.company.events.TickTempoChange;

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
    protected void playNote(int note, int numericalNote, boolean on, int track) {
        System.out.println(numericalNote + " " + note);
        if(on) {
            midiChannels[0].noteOn(numericalNote, 100);
        } else {
            midiChannels[0].noteOff(numericalNote);
        }
    }
}