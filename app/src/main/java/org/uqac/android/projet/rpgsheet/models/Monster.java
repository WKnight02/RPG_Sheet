package org.uqac.android.projet.rpgsheet.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Bruno.J on 26/02/2017.
 */

public class Monster {

    final public int NAME_MIN_CHARACTERS = 3;

    protected long id;
    protected String name;
    protected int health = 0;
    protected int strength = 0;

    public Monster(String name) {
        setName(name);
        this.id=-1;
    }

    public Monster setName(String name) {
        if (name.length() >= NAME_MIN_CHARACTERS) this.name = name;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Name should be at least 3 characters long (got %d)", name.length()));
        return this;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Monster setHealth(int health) {
        this.health = health;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public Monster setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public long getId(){ return this.id;}

    public void setId(long id){this.id=id;}

    public String toString(){
        return "Name: "+this.name;
    }
}
