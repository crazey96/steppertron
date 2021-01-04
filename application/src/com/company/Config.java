package com.company;

import java.util.HashMap;
import java.util.Map;

public final class Config {

    public static final String[] NOTE_NAMES = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    // frequencies converted into 50% duty cycle delay (microseconds)
    // time period = 1 / frequency
    // full delay (microseconds) = time period * 1000000
    // delay (50% duty cycle) = full delay / 2;
    // complete calculation: delay = ((1 / frequency) * 1000000) / 2
    public static final Map<String, Integer> NotesToDelayInMicroseconds = new HashMap<>() {{
        put("C8", 119);
        put("B7", 126);
        put("A#7", 134);
        put("A7", 142);
        put("G#7", 150);
        put("G7", 159);
        put("F#7", 168);
        put("F7", 178);
        put("E7", 189);
        put("D#7", 200);
        put("D7", 212);
        put("C#7", 225);
        put("C7", 238);
        put("B6", 253);
        put("A#6", 268);
        put("A6", 284);
        put("G#6", 300);
        put("G6", 318);
        put("F#6", 337);
        put("F6", 357);
        put("E6", 379);
        put("D#6", 401);
        put("D6", 425);
        put("C#6", 450);
        put("C6", 477);
        put("B5", 506);
        put("A#5", 536);
        put("A5", 568);
        put("G#5", 601);
        put("G5", 637);
        put("F#5", 675);
        put("F5", 715);
        put("E5", 758);
        put("D#5", 803);
        put("D5", 851);
        put("C#5", 901);
        put("C5", 955);
        put("B4", 1012);
        put("A#4", 1072);
        put("A4", 1136);
        put("G#4", 1203);
        put("G4", 1275);
        put("F#4", 1351);
        put("F4", 1431);
        put("E4", 1516);
        put("D#4", 1607);
        put("D4", 1702);
        put("C#4", 1803);
        put("C4", 1911);
        put("B3", 2024);
        put("A#3", 2145);
        put("A3", 2272);
        put("G#3", 2407);
        put("G3", 2551);
        put("F#3", 2702);
        put("F3", 2863);
        put("E3", 3033);
        put("D#3", 3214);
        put("D3", 3405);
        put("C#3", 3607);
        put("C3", 3822);
        put("B2", 4049);
        put("A#2", 4290);
        put("A2", 4545);
        put("G#2", 4815);
        put("G2", 5102);
        put("F#2", 5405);
        put("F2", 5726);
        put("E2", 6067);
        put("D#2", 6428);
        put("D2", 6810);
        put("C#2", 7215);
        put("C2", 7644);
        put("B1", 8099);
        put("A#1", 8580);
        put("A1", 9090);
        put("G#1", 9631);
        put("G1", 10204);
        put("F#1", 10810);
        put("F1", 11453);
        put("E1", 12134);
        put("D#1", 12856);
        put("D1", 13620);
        put("C#1", 14430);
        put("C1", 15289);
        put("B0", 16198);
        put("A#0", 17161);
        put("A0", 18181);
        put("G#0", 19262);
        put("G0", 20408);
        put("F#0", 21621);
        put("F0", 22907);
        put("E0", 24269);
        put("D#0", 25713);
        put("D0", 27242);
        put("C#0", 28861);
        put("C0", 30578);
    }};
}