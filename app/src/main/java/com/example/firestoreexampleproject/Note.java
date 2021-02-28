package com.example.firestoreexampleproject;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

public class Note {

        private String title,description,noteID;
        private int priority;
        private Map<String,Boolean> tags;


    public Note() {}

        public Note(String title, String description,int priority, Map<String,Boolean> tags) {
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.tags = tags;
        }

        @Exclude                                                                                    // not going to include the id in firestore, it's redudant;
    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    public int getPriority() {
        return priority;
    }

    public  Map<String,Boolean>  getTags() {
        return tags;
    }
}


