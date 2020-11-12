package com.company;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        MidiParser midiParser = new MidiParser();
        midiParser.parseMidiFile(new File("src/samples/starwars.mid"));
    }
}