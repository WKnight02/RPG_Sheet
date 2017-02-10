package org.uqac.android.projet.rpgsheet.models;

import java.util.Locale;

/**
 * Created by White_Knight02 on 09/02/2017.
 * This class defines a character, as some will be created by a player.
 * This stores everything, from name to inventory, skills, etc...
 */

public class Character {

    final public int NAME_MIN_CHARACTERS = 3;

    protected String name;
    //protected Inventory inventory;
    //protected List<Trait> traits;
    //protected List<Info> infos;

    public Character(String name) {
        setName(name);
    }

    public Character setName(String name) {
        if (name.length() > NAME_MIN_CHARACTERS) this.name = name;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Name should be at least 3 characters long (got %d)", name.length()));
        return this;
    }

    public String getName() {
        return name;
    }
}
