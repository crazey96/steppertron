package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;
import com.company.events.TickTempoChange;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

public class SteppertronSimulation {

    private final ArrayList<Tick> ticks;
    private final int resolution;

    private final MidiChannel[] midiChannels;

    public SteppertronSimulation(int resolution, ArrayList<Tick> ticks) throws MidiUnavailableException {
        this.resolution = resolution;
        this.ticks = ticks;
        // initialize synthesizer and channels
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        this.midiChannels = synthesizer.getChannels();
    }
    public void play() {
        long tickCount = 0;
        long maxCount = ticks.get(ticks.size() - 1).getNumber();
        int microsecondsPerTick = 1;
        while (tickCount < maxCount) {
            for(Tick tick : ticks) {
                if(tick.getNumber() > tickCount) {
                    break;
                }
                if(tick.getNumber() == tickCount) {
                    if(tick instanceof TickNote) {
                        TickNote tickNote = (TickNote) tick;
                        playNote(tickNote.getChannel(), tickNote.getGeneralNote(), tickNote.getOn(), tickNote.getVelocity());
                        //System.out.println("note " + tickNote.getOn() + " " + Config.NOTE_NAMES[tickNote.getNote()] + tickNote.getOctave());
                    } else if(tick instanceof TickTempoChange) {
                        TickTempoChange tickTempoChange = (TickTempoChange) tick;
                        microsecondsPerTick = (int)((60000 / ((double)tickTempoChange.getTempo() * resolution)) * 1000);
                        //System.out.println("new bpm: " + tickTempoChange.getTempo());
                    }
                }
            }
            tickCount += 1;
            sleep(microsecondsPerTick);
        }
    }
    private void playNote(int channel, int note, boolean on, int velocity) {
        if(on) {
            midiChannels[channel].noteOn(note, velocity);
        } else {
            midiChannels[channel].noteOff(note);
        }
    }
    // sleep (time) microseconds
    private void sleep(int time) {
        long initTime = System.nanoTime();
        while(true) {
            if(System.nanoTime() - initTime > time * 1000) {
                return;
            }
        }
    }
}