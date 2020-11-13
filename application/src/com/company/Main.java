package com.company;

import com.company.events.Tick;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        MidiParser midiParser = new MidiParser(new File("src/samples/supermario.mid"));
        ArrayList<Tick> ticks = midiParser.parseMidiFile();
        SteppertronSimulation steppertronSimulation = new SteppertronSimulation(midiParser.getSequence(), ticks);
        steppertronSimulation.play();
    }
}