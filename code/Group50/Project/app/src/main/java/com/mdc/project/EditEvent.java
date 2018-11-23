package com.mdc.project;

import android.widget.EditText;

public class EditEvent {
    private Character character;

    public EditEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
