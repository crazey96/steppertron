package com.company;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class Main {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int CONTINUOUS_CONTROLLER = 0xB0;
    public static final int PATCH_CHANGE = 0xC0;

    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        File file = new File("src/samples/starwars.mid");
        parseMidi(file);
    }
    private static void parseMidi(File file) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(file);
        for (int trackIndex = 0; trackIndex < sequence.getTracks().length; trackIndex++) {
            parseTrack(sequence.getTracks()[trackIndex], trackIndex);
        }
    }
    private static void parseTrack(Track track, int trackNumber) {
        System.out.println("Track: " + trackNumber + " "
                + "size: " + track.size());
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();
            parseMessage(message, event.getTick());
        }
    }
    private static void parseMessage(MidiMessage message, long eventTick) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            switch (shortMessage.getCommand()) {
                case NOTE_ON -> {
                    int noteOnKey = shortMessage.getData1();
                    int noteOnOctave = noteOnKey / 12 - 1;
                    int noteOn = noteOnKey % 12;
                    System.out.println("@" + eventTick + " "
                            + "Channel: " + shortMessage.getChannel() + " "
                            + "Note: " + NOTE_NAMES[noteOn] + noteOnOctave + " "
                            + "ON"
                    );
                }
                case NOTE_OFF -> {
                    int noteOffKey = shortMessage.getData1();
                    int noteOffOctave = noteOffKey / 12 - 1;
                    int noteOff = noteOffKey % 12;
                    System.out.println("@" + eventTick + " "
                            + "Channel: " + shortMessage.getChannel() + " "
                            + "Note: " + NOTE_NAMES[noteOff] + noteOffOctave + " "
                            + "OFF"
                    );
                }
                case CONTINUOUS_CONTROLLER -> {
                    int continuousControllerParam1 = shortMessage.getData1();
                    int continuousControllerParam2 = shortMessage.getData2();
                    System.out.println("@" + eventTick + " "
                            + "continousControllerParam1: " + continuousControllerParam1 + " "
                            + "continousControllerParam2: " + continuousControllerParam2
                    );
                }
                case PATCH_CHANGE -> { }
                default -> {
                    System.err.println("Not implemented yet: " + shortMessage.getCommand());
                }
            }
        } else if(message instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) message;
            switch (metaMessage.getType()) {
                // track name
                case 0x03 -> {
                    System.out.println("Track name: " + new String(metaMessage.getData()));
                }


                // time signature (for example 3/4)
                case 0x58 -> {
                    byte[] signature = metaMessage.getData();
                    System.out.print("Time signature: ");
                    for(byte b : signature) {
                        System.out.print(b + " ");
                    }
                    System.out.println();
                }
                // tempo (microseconds per beat)
                case 0x51 -> {
                    byte[] tempo = metaMessage.getData();
                    System.out.print("Tempo: " + new BigInteger(tempo).intValue());
                }
                default -> {
                    System.err.println("MetaMessage not implemented yet (Type): " + metaMessage.getType());
                }
            }
        } else if(message instanceof SysexMessage) {
            SysexMessage sysexMessage = (SysexMessage) message;
            //System.err.println("SysexMessage: " + sysexMessage.toString());
        } else {
            System.err.println("Not implemented yet: " + message.getClass());
        }
    }
}