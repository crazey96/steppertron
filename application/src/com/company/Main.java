package com.company;

import com.company.events.Tick;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        MidiParser midiParser = new MidiParser();
        ArrayList<Tick> ticks = midiParser.parseMidiFile(new File("src/samples/starwars.mid"));
        for(Tick tick : ticks) {
            System.out.println(
                    tick.getNumber()
                    + " "
                    + tick.getType()
            );
        }
    }
}