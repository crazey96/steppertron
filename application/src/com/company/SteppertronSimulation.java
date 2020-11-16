package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;
import com.company.events.TickTempoChange;

import javax.sound.midi.*;
import java.util.ArrayList;

public class SteppertronSimulation {

    private final ArrayList<Tick> ticks;
    private final Sequence sequence;

    private final MidiChannel[] midiChannels;

    private int microsecondsPerTick = 1;

    public SteppertronSimulation(Sequence sequence, ArrayList<Tick> ticks) throws MidiUnavailableException {
        this.sequence = sequence;
        this.ticks = ticks;
        // initialize synthesizer and channels
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        this.midiChannels = synthesizer.getChannels();
    }
    public void play() {
        long tickCount = 0;
        long maxCount = ticks.get(ticks.size() - 1).getNumber();
        while (tickCount < maxCount) {
            long initTime = System.nanoTime();
            handleNextTickCount(tickCount);
            tickCount += 1;
            sleep(microsecondsPerTick, initTime);
        }
    }
    private void handleNextTickCount(long tickCount) {
        for(Tick tick : ticks) {
            if(tick.getNumber() > tickCount) {
                break;
            }
            if(tick.getNumber() == tickCount) {
                if(tick instanceof TickNote) {
                    TickNote tickNote = (TickNote) tick;
                    playNote(tickNote.getChannel(), tickNote.getGeneralNote(), tickNote.getOn(), tickNote.getVelocity());
                } else if(tick instanceof TickTempoChange) {
                    TickTempoChange tickTempoChange = (TickTempoChange) tick;
                    microsecondsPerTick = (int)((60000 / ((double)tickTempoChange.getTempo() * sequence.getResolution())) * 1000);
                }
            }
        }
    }
    // sleep (time) microseconds
    private void sleep(int time, long initTime) {
        while(true) {
            if(System.nanoTime() - initTime > time * 1000) {
                return;
            }
        }
    }
    private void playNote(int channel, int note, boolean on, int velocity) {
        if(on) {
            midiChannels[channel].noteOn(note, velocity);
        } else {
            midiChannels[channel].noteOff(note);
        }
    }
}