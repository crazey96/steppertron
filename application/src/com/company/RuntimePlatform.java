package com.company;

import com.company.events.Tick;

import java.util.ArrayList;

public class RuntimePlatform {

    private final ArrayList<Tick> ticks;

    public RuntimePlatform(ArrayList<Tick> ticks) {
        this.ticks = ticks;
    }
}