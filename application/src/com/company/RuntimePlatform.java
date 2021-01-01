package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;
import com.company.events.TickTempoChange;

import javax.sound.midi.Sequence;
import java.util.ArrayList;

public class RuntimePlatform extends SerialService implements Runnable {

    private final ArrayList<Tick> ticks;
    private final Sequence sequence;

    private int microsecondsPerTick = 1;

    public RuntimePlatform(Sequence sequence, ArrayList<Tick> ticks) {
        this.sequence = sequence;
        this.ticks = ticks;
    }
    @Override
    public void run() {
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
                return;
            }
            if(tick.getNumber() == tickCount) {
                if(tick instanceof TickNote) {
                    TickNote tickNote = (TickNote) tick;
                    playNote(tickNote.getGeneralNote(), tickNote.getOn(), tickNote.getTrack());
                } else if(tick instanceof TickTempoChange) {
                    TickTempoChange tickTempoChange = (TickTempoChange) tick;
                    microsecondsPerTick = (int)((60000 / ((double)tickTempoChange.getTempo() * sequence.getResolution())) * 1000);
                }
            }
        }
    }
    // sleep (in microseconds)
    private void sleep(int time, long initTime) {
        while(true) {
            if(System.nanoTime() - initTime > time * 1000L) {
                return;
            }
        }
    }
    protected void playNote(int note, boolean on, int track) {
        write("Note: " + note + " "
                + "on: " + on + " "
                + "track: " + track
        );
    }
}