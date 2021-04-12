package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;

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
    protected void noteAction(TickNote tickNote) {
        if(midiChannels == null) {
            return;
        }
        if(tickNote.getOn()) {
            steppertron.addNote(tickNote.getNote(), tickNote.getTrack(), tickNote.getChannel());
            midiChannels[0].noteOn(tickNote.getGeneralNote(), 100);
        } else {
            steppertron.removeNote(tickNote.getNote(), tickNote.getTrack(), tickNote.getChannel());
            midiChannels[0].noteOff(tickNote.getGeneralNote());
        }
    }
}