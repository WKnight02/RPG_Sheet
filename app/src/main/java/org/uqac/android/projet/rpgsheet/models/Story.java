package org.uqac.android.projet.rpgsheet.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Bruno.J on 26/02/2017.
 */

public class Story {
    final public int NAME_MIN_CHARACTERS = 3;

    protected long id;
    protected String title;
    protected String lore;
    protected HashMap<String, Monster> monsters;

    public Story(String title) {
        monsters = new HashMap<String, Monster>();
        setTitle(title);
        setLore("");
        this.id=-1;
    }

    public Story setTitle(String title) {
        if (title.length() > NAME_MIN_CHARACTERS) this.title = title;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Name should be at least 3 characters long (got %d)", title.length()));
        return this;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Adds a trait to the character
     */
    public Story addMonster(Monster monster) {
        String label = monster.getName();
        if (monsters.containsKey(label))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait seems to already exists... (%s)", label));
        else monsters.put(label, monster);
        return this;
    }

    public Monster getMonster(String label) {
        if (monsters.containsKey(label)) return monsters.get(label);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait doesn't seem to exists... (%s)", label));
    }

    public Collection<Monster> getAllMonster() {
        return monsters.values();
    }

    public String getLore(){ return this.lore;}

    public void setLore(String lore) {
        this.lore=lore;
    }

    public long getId(){ return this.id;}

    public void setId(long id){this.id=id;}

    public String toString(){
        return "title: "+this.title;
    }
}