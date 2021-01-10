package com.company;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        //MidiParser midiParser = new MidiParser(new File("src/samples/tetris.mid"));
        //SteppertronSimulation steppertronSimulation = new SteppertronSimulation(midiParser.getSequence(), midiParser.parseMidiFile());
        //steppertronSimulation.run();
        MidiParser midiParser = new MidiParser(new File("samples/tetris.mid"));
        RuntimePlatform runtimePlatform = new RuntimePlatform(midiParser.getSequence(), midiParser.parseMidiFile());
        runtimePlatform.run();
    }
}