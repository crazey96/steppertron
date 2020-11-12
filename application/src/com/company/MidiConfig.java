package com.company;

public final class MidiConfig {

    // voice messages
    public static final int NOTE_OFF = 0x80;
    public static final int NOTE_ON = 0x90;
    public static final int POLYPHONIC_KEY_PRESSURE = 0xA0;
    public static final int CONTROL_CHANGE = 0xB0;
    public static final int PROGRAM_CHANGE = 0xC0;
    public static final int CHANNEL_PRESSURE = 0xD0;
    public static final int PITCH_BEND = 0XE0;

    // meta messages
    public static final int SEQUENCE_NUMBER = 0x00;
    public static final int TEXT = 0x01;
    public static final int COPYRIGHT_NOTICE = 0x02;
    public static final int TRACK_NAME = 0x03;
    public static final int INSTRUMENT_NAME = 0x04;
    public static final int LYRICS = 0x05;
    public static final int MARKER = 0X06;
    public static final int CUE_POINT = 0x07;
    public static final int CHANNEL_PREFIX = 0x20;
    public static final int END_OF_TRACK = 0x2F;
    public static final int SET_TEMPO = 0x51;
    public static final int SMPTE_OFFSET = 0x54;
    public static final int TIME_SIGNATURE = 0x58;
    public static final int KEY_SIGNATURE = 0X59;
    public static final int SEQUENCER_SPECIFIC = 0X7F;
}