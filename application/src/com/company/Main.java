package com.company;

import com.company.events.Tick;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException, InterruptedException, MidiUnavailableException {
        MidiParser midiParser = new MidiParser();
        ArrayList<Tick> ticks = midiParser.parseMidiFile(new File("src/samples/supermario.mid"));
        SteppertronSimulation steppertronSimulation = new SteppertronSimulation(ticks);
        steppertronSimulation.play();
    }
}