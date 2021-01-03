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

    private void parseMidiMessage(MidiMessage midiMessage, long eventTick, int trackIndex, ArrayList<Tick> ticks) {
        if (midiMessage instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) midiMessage;
            parseShortMessage(shortMessage, eventTick, trackIndex, ticks);
        } else if (midiMessage instanceof MetaMessage) {
            MetaMessage metaMessage = (MetaMessage) midiMessage;
            parseMetaMessage(metaMessage, eventTick, ticks);
        }
    }

    private void parseShortMessage(ShortMessage shortMessage, long eventTick, int trackIndex, ArrayList<Tick> ticks) {
        switch (shortMessage.getCommand()) {
            case MidiConfig.NOTE_OFF: {
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
                break;
            }
            case MidiConfig.NOTE_ON: {
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
                break;
            }
            case MidiConfig.POLYPHONIC_KEY_PRESSURE:
                break;
            case MidiConfig.CONTROL_CHANGE:
                break;
            case MidiConfig.PROGRAM_CHANGE:
                break;
            case MidiConfig.CHANNEL_PRESSURE:
                break;
            case MidiConfig.PITCH_BEND:
                break;
            default: {
                System.err.println("@" + eventTick + " "
                        + "Not implemented yet: "
                        + shortMessage.getCommand());
                break;
            }
        }
    }

    private void parseMetaMessage(MetaMessage metaMessage, long eventTick, ArrayList<Tick> ticks) {
        switch (metaMessage.getType()) {
            case MidiConfig.SEQUENCE_NUMBER:
                break;
            case MidiConfig.TEXT:
                break;
            case MidiConfig.COPYRIGHT_NOTICE:
                break;
            case MidiConfig.TRACK_NAME:
                break;
            case MidiConfig.INSTRUMENT_NAME:
                break;
            case MidiConfig.LYRICS:
                break;
            case MidiConfig.MARKER:
                break;
            case MidiConfig.CUE_POINT:
                break;
            case MidiConfig.CHANNEL_PREFIX:
                break;
            case MidiConfig.END_OF_TRACK:
                break;
            case MidiConfig.SET_TEMPO: {
                byte[] tempo = metaMessage.getData();
                ticks.add(new TickTempoChange(
                        eventTick,
                        Tick.Type.TickTempoChange,
                        60000000 / new BigInteger(tempo).intValue()
                ));
                break;
            }
            case MidiConfig.SMPTE_OFFSET:
                break;
            case MidiConfig.TIME_SIGNATURE: {
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
                break;
            }
            case MidiConfig.KEY_SIGNATURE:
                break;
            case MidiConfig.SEQUENCER_SPECIFIC:
                break;
            default:
                System.err.println("@" + eventTick + " "
                        + "MetaMessage not implemented yet (Type): "
                        + metaMessage.getType());
                break;
        }
    }
}