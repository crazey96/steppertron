package com.company;

public class Steppertron {

    private final ActiveNote[] activeNotes;

    public Steppertron(int motors) {
        this.activeNotes = new ActiveNote[motors];
    }
    public int addNote(String note, int track, int channel) {
        int index = getNextAvailableIndex();
        this.activeNotes[index] = new ActiveNote(note, track, channel);
        return index;
    }
    public int removeNote(String note, int track, int channel) {
        for(int index = 0; index < activeNotes.length; index++) {
            if(activeNotes[index] != null
                    && activeNotes[index].getNote().equals(note)
                    && activeNotes[index].getTrack() == track
                    && activeNotes[index].getChannel() == channel
            ) {
                this.activeNotes[index] = null;
                return index;
            }
        }
        return -1;
    }
    private int getNextAvailableIndex() {
        int index = -1;
        do {
            index++;
            if(index > activeNotes.length) {
                return activeNotes.length;
            }
        } while(!(activeNotes[index] == null));
        return index;
    }
    private class ActiveNote {

        private String note;
        private int track;
        private int channel;

        public ActiveNote(String note, int track, int channel) {
            this.note = note;
            this.track = track;
            this.channel = channel;
        }
        public String getNote() {
            return note;
        }
        public int getTrack() {
            return track;
        }
        public int getChannel() {
            return channel;
        }
    }
}