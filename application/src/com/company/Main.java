package com.company;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        //MidiParser midiParser = new MidiParser(new File("src/samples/supermario.mid"));
        MidiParser midiParser = new MidiParser(new File("samples/supermario.mid"));
        SteppertronSimulation steppertronSimulation = new SteppertronSimulation(midiParser.getSequence(), midiParser.parseMidiFile());
        steppertronSimulation.run();
    }
}