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

    private final Sequence sequence;

    public MidiParser(File file) throws InvalidMidiDataException, IOException {
        this.sequence = MidiSystem.getSequence(file);

    }
    public Sequence getSequence() {
        return sequence;
    }
    public ArrayList<Tick> parseMidiFile() {
        ArrayList<Tick> ticks = new ArrayList<>();
        for (int trackIndex = 0; trackIndex < sequence.getTracks().length; trackIndex++) {
            parseTrack(sequence.getTracks()[trackIndex], ticks, trackIndex);
        }
        Collections.sort(ticks);
        return ticks;
    }
    private void parseTrack(Track track, ArrayList<Tick> ticks, int trackIndex) {
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage midiMessage = event.getMessage();
            parseMidiMessage(midiMessage, event.getTick(), trackIndex, ticks);
        }
    }
    private void parseMidiMessage(MidiMessage midiMessage, long eventTick, int trackIndex,  ArrayList<Tick> ticks) {
        if (midiMessage instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) midiMessage;
            parseShortMessage(shortMessage, eventTick, trackIndex, ticks);
        } else if(midiMessage instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) midiMessage;
            parseMetaMessage(metaMessage, eventTick, ticks);
        }
    }
    private void parseShortMessage(ShortMessage shortMessage, long eventTick, int trackIndex, ArrayList<Tick> ticks) {
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
                        false,
                        trackIndex));
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
                        true,
                        trackIndex));
            }
            case MidiConfig.POLYPHONIC_KEY_PRESSURE -> { }
            case MidiConfig.CONTROL_CHANGE -> { }
            case MidiConfig.PROGRAM_CHANGE -> { }
            case MidiConfig.CHANNEL_PRESSURE -> { }
            case MidiConfig.PITCH_BEND -> { }
            default -> {
                System.err.println("@" + eventTick + " "
                        + "Not implemented yet: "
                        + shortMessage.getCommand());
            }
        }
    }
    private void parseMetaMessage(MetaMessage metaMessage, long eventTick, ArrayList<Tick> ticks) {
        switch (metaMessage.getType()) {
            case MidiConfig.SEQUENCE_NUMBER -> { }
            case MidiConfig.TEXT -> { }
            case MidiConfig.COPYRIGHT_NOTICE -> { }
            case MidiConfig.TRACK_NAME -> { }
            case MidiConfig.INSTRUMENT_NAME -> { }
            case MidiConfig.LYRICS -> { }
            case MidiConfig.MARKER -> { }
            case MidiConfig.CUE_POINT -> { }
            case MidiConfig.CHANNEL_PREFIX -> { }
            case MidiConfig.END_OF_TRACK -> { }
            case MidiConfig.SET_TEMPO -> {
                byte[] tempo = metaMessage.getData();
                ticks.add(new TickTempoChange(
                        eventTick,
                        Tick.Type.TickTempoChange,
                        60000000 / new BigInteger(tempo).intValue()
                ));
            }
            case MidiConfig.SMPTE_OFFSET -> { }
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
            case MidiConfig.KEY_SIGNATURE -> { }
            case MidiConfig.SEQUENCER_SPECIFIC -> { }
            default -> {
                System.err.println("@" + eventTick + " "
                        + "MetaMessage not implemented yet (Type): "
                        + metaMessage.getType());
            }
        }
    }
}