package com.company;

public class Steppertron {

    private final String[] activeNotes;

    public Steppertron(int motors) {
        this.activeNotes = new String[motors];
    }
    public int addNote(String note) {
        int index = getNextAvailableIndex();
        this.activeNotes[index] = note;
        return index;
    }
    public int removeNote(String note) {
        int index = getIndexByNote(note);
        this.activeNotes[index] = null;
        return index;
    }
    private int getNextAvailableIndex() {
        int index = -1;
        do {
            index++;
        } while(!(activeNotes[index] == null));
        return index;
    }
    private int getIndexByNote(String note) {
        for(int index = 0; index < activeNotes.length; index++) {
            if(activeNotes[index].equals(note)) {
                return index;
            }
        }
        return 0;
    }
}