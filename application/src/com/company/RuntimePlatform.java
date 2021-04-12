package com.company;

import com.company.events.Tick;
import com.company.events.TickNote;
import com.company.events.TickTempoChange;
import com.company.serial.SerialService;

import javax.sound.midi.Sequence;
import java.util.*;

public class RuntimePlatform extends SerialService implements Runnable {

    private final ArrayList<Tick> ticks;
    private final Sequence sequence;

    private int microsecondsPerTick = 1;

    protected Steppertron steppertron;

    public RuntimePlatform(Sequence sequence, ArrayList<Tick> ticks) {
        this.sequence = sequence;
        this.ticks = ticks;
        this.steppertron = new Steppertron(Config.SLAVES * Config.MOTORS);
        this.initialize();
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
                    noteAction(tickNote);
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
    protected void noteAction(TickNote tickNote) {
        if(tickNote.getOn()) {
            int index = steppertron.addNote(tickNote.getNote(), tickNote.getTrack(), tickNote.getChannel());
            if(index != -1) {
                int slave = index / Config.MOTORS;
                int slaveMotor = index % Config.MOTORS;
                write(slave,tickNote.getNote() + " true " + slaveMotor);
            }
        } else {
            int index = steppertron.removeNote(tickNote.getNote(), tickNote.getTrack(), tickNote.getChannel());
            if(index != -1) {
                int slave = index / Config.MOTORS;
                int slaveMotor = index % Config.MOTORS;
                write(slave,tickNote.getNote() + " false " + slaveMotor);
            }
        }
    }
}