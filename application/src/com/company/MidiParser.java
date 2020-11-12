package com.company;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class MidiParser {

    public void parseMidiFile(File file) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(file);
        for (int trackIndex = 0; trackIndex < sequence.getTracks().length; trackIndex++) {
            parseTrack(sequence.getTracks()[trackIndex], trackIndex);
        }
    }
    private void parseTrack(Track track, int trackNumber) {
        System.out.println("Track: " + trackNumber + " "
                + "size: " + track.size());
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage midiMessage = event.getMessage();
            parseMidiMessage(midiMessage, event.getTick());
        }
    }
    private void parseMidiMessage(MidiMessage midiMessage, long eventTick) {
        if (midiMessage instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) midiMessage;
            parseShortMessage(shortMessage, eventTick);
        } else if(midiMessage instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) midiMessage;
            parseMetaMessage(metaMessage, eventTick);
        }
    }
    private void parseShortMessage(ShortMessage shortMessage, long eventTick) {
        switch (shortMessage.getCommand()) {
            case MidiConfig.NOTE_OFF -> {
                int noteOffKey = shortMessage.getData1();
                int noteOffOctave = noteOffKey / 12 - 1;
                int noteOff = noteOffKey % 12;
                System.out.println("@" + eventTick + " "
                        + "Channel: " + shortMessage.getChannel() + " "
                        + "Note: " + Config.NOTE_NAMES[noteOff] + noteOffOctave + " "
                        + "OFF"
                );
            }
            case MidiConfig.NOTE_ON -> {
                int noteOnKey = shortMessage.getData1();
                int noteOnOctave = noteOnKey / 12 - 1;
                int noteOn = noteOnKey % 12;
                System.out.println("@" + eventTick + " "
                        + "Channel: " + shortMessage.getChannel() + " "
                        + "Note: " + Config.NOTE_NAMES[noteOn] + noteOnOctave + " "
                        + "ON"
                );
            }
            case MidiConfig.POLYPHONIC_KEY_PRESSURE -> {
                // TODO: implement polyphonic key pressure
            }
            case MidiConfig.CONTROL_CHANGE -> {
                // TODO: implement control change
            }
            case MidiConfig.PROGRAM_CHANGE -> {
                // TODO: implement program change
            }
            case MidiConfig.CHANNEL_PRESSURE -> {
                // TODO: implement channel pressure
            }
            case MidiConfig.PITCH_BEND -> {
                // TODO: implement pitch bend
            }
            default -> {
                System.err.println("@" + eventTick + " "
                        + "Not implemented yet: "
                        + shortMessage.getCommand());
            }
        }
    }
    private void parseMetaMessage(MetaMessage metaMessage, long eventTick) {
        switch (metaMessage.getType()) {
            case MidiConfig.SEQUENCE_NUMBER -> {
                // TODO: implement sequence number
            }
            case MidiConfig.TEXT -> {
                // TODO: implement text
            }
            case MidiConfig.COPYRIGHT_NOTICE -> {
                // TODO: copyright notice
            }
            case MidiConfig.TRACK_NAME -> {
                System.out.println("@" + eventTick + " "
                        + "Track name: "
                        + new String(metaMessage.getData()));
            }
            case MidiConfig.INSTRUMENT_NAME -> {
                // TODO: instrument name
            }
            case MidiConfig.LYRICS -> {
                // TODO: lyrics
            }
            case MidiConfig.MARKER -> {
                // TODO: marker
            }
            case MidiConfig.CUE_POINT -> {
                // TODO: cue point
            }
            case MidiConfig.CHANNEL_PREFIX -> {
                // TODO: channel prefix
            }
            case MidiConfig.END_OF_TRACK -> {
                // TODO: end of track
            }
            case MidiConfig.SET_TEMPO -> {
                byte[] tempo = metaMessage.getData();
                System.out.println("@" + eventTick + " "
                        + "Tempo: "
                        + new BigInteger(tempo).intValue());
            }
            case MidiConfig.SMPTE_OFFSET -> {
                // TODO: implement smpte offset
            }
            case MidiConfig.TIME_SIGNATURE -> {
                // TODO: implement time signature
            }
            case MidiConfig.KEY_SIGNATURE -> {
                // TODO: key signature
            }
            case MidiConfig.SEQUENCER_SPECIFIC -> {
                // TODO: sequencer specific
            }
            default -> {
                System.err.println("@" + eventTick + " "
                        + "MetaMessage not implemented yet (Type): "
                        + metaMessage.getType());
            }
        }
    }
}