package com.company;

import com.company.events.TickNote;
import com.company.events.TickTempoChange;
import com.company.events.Tick;
import com.company.events.TickTimeSignature;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class MidiParser {

    public ArrayList<Tick> parseMidiFile(File file) throws InvalidMidiDataException, IOException {
        ArrayList<Tick> ticks = new ArrayList<>();
        Sequence sequence = MidiSystem.getSequence(file);
        for (int trackIndex = 0; trackIndex < sequence.getTracks().length; trackIndex++) {
            parseTrack(sequence.getTracks()[trackIndex], ticks);
        }
        Collections.sort(ticks);
        return ticks;
    }
    private void parseTrack(Track track, ArrayList<Tick> ticks) {
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage midiMessage = event.getMessage();
            parseMidiMessage(midiMessage, event.getTick(), ticks);
        }
    }
    private void parseMidiMessage(MidiMessage midiMessage, long eventTick, ArrayList<Tick> ticks) {
        if (midiMessage instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) midiMessage;
            parseShortMessage(shortMessage, eventTick, ticks);
        } else if(midiMessage instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) midiMessage;
            parseMetaMessage(metaMessage, eventTick, ticks);
        }
    }
    private void parseShortMessage(ShortMessage shortMessage, long eventTick, ArrayList<Tick> ticks) {
        switch (shortMessage.getCommand()) {
            case MidiConfig.NOTE_OFF -> {
                int noteOffKey = shortMessage.getData1();
                int noteOffOctave = noteOffKey / 12 - 1;
                int noteOff = noteOffKey % 12;
                int velocity = shortMessage.getData2();
                ticks.add(new TickNote(
                        eventTick,
                        Tick.Type.TickNote,
                        noteOffKey,
                        noteOff,
                        noteOffOctave,
                        velocity,
                        shortMessage.getChannel(),
                        false));
            }
            case MidiConfig.NOTE_ON -> {
                int noteOnKey = shortMessage.getData1();
                int noteOnOctave = noteOnKey / 12 - 1;
                int noteOn = noteOnKey % 12;
                int velocity = shortMessage.getData2();
                ticks.add(new TickNote(
                        eventTick,
                        Tick.Type.TickNote,
                        noteOnKey,
                        noteOn,
                        noteOnOctave,
                        velocity,
                        shortMessage.getChannel(),
                        true));
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
    private void parseMetaMessage(MetaMessage metaMessage, long eventTick, ArrayList<Tick> ticks) {
        switch (metaMessage.getType()) {
            case MidiConfig.SEQUENCE_NUMBER -> {
                // TODO: implement sequence number
            }
            case MidiConfig.TEXT -> {
                // TODO: implement text
            }
            case MidiConfig.COPYRIGHT_NOTICE -> {
                // TODO: implement copyright notice
            }
            case MidiConfig.TRACK_NAME -> {
                // TODO: implement track name
            }
            case MidiConfig.INSTRUMENT_NAME -> {
                // TODO: instrument name
            }
            case MidiConfig.LYRICS -> {
                // TODO: implement lyrics
            }
            case MidiConfig.MARKER -> {
                // TODO: implement marker
            }
            case MidiConfig.CUE_POINT -> {
                // TODO: implement cue point
            }
            case MidiConfig.CHANNEL_PREFIX -> {
                // TODO: implement channel prefix
            }
            case MidiConfig.END_OF_TRACK -> {
                // TODO: implement end of track
            }
            case MidiConfig.SET_TEMPO -> {
                byte[] tempo = metaMessage.getData();
                ticks.add(new TickTempoChange(
                        eventTick,
                        Tick.Type.TickTempoChange,
                        new BigInteger(tempo).intValue()
                ));
            }
            case MidiConfig.SMPTE_OFFSET -> {
                // TODO: implement smpte offset
            }
            case MidiConfig.TIME_SIGNATURE -> {
                byte[] timeSignature = metaMessage.getData();
                int numerator = timeSignature[0];
                int denominator = timeSignature[1] + timeSignature[1];
                //int metronomeClickPerMidiClick = timeSignature[2];
                //int notesPerBeatIn32 = timeSignature[3];
                ticks.add(new TickTimeSignature(
                        eventTick,
                        Tick.Type.TickTimeSignature,
                        numerator, denominator
                ));
            }
            case MidiConfig.KEY_SIGNATURE -> {
                // TODO: implement key signature
            }
            case MidiConfig.SEQUENCER_SPECIFIC -> {
                // TODO: implement sequencer specific
            }
            default -> {
                System.err.println("@" + eventTick + " "
                        + "MetaMessage not implemented yet (Type): "
                        + metaMessage.getType());
            }
        }
    }
}