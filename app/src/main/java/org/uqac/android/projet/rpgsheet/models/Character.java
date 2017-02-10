package org.uqac.android.projet.rpgsheet.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by White_Knight02 on 09/02/2017.
 * This class defines a character, as some will be created by a player.
 * This stores everything, from name to inventory, skills, etc...
 */

public class Character {

    final public int NAME_MIN_CHARACTERS = 3;

    protected String name;
    protected Inventory inventory;
    protected HashMap<String, Trait> traits;
    protected HashMap<String, Info> infos;

    public Character(String name) {
        inventory = new Inventory();
        traits = new HashMap<String, Trait>();
        infos = new HashMap<String, Info>();
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

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Adds a trait to the character
     */
    public Character addTrait(Trait trait) {
        String label = trait.getLabel();
        if (traits.containsKey(label))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait seems to already exists... (%s)", label));
        else traits.put(label, trait);
        return this;
    }

    public Trait getTrait(String label) {
        if (traits.containsKey(label)) return traits.get(label);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait doesn't seem to exists... (%s)", label));
    }

    public Collection<Trait> getAllTraits() {
        return traits.values();
    }

    /**
     * Adds an info to the character
     */
    public Character addInfo(Info info) {
        String label = info.getLabel();
        if (traits.containsKey(label))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This info seems to already exists... (%s)", label));
        else infos.put(label, info);
        return this;
    }

    public Info getInfo(String label) {
        if (infos.containsKey(label)) return infos.get(label);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This info doesn't seem to exists... (%s)", label));
    }

    public Collection<Info> getAllInfos() {
        return infos.values();
    }
}
