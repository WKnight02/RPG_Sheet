package org.uqac.android.projet.rpgsheet.models;

import java.util.Locale;

/**
 * Created by White_Knight02 on 09/02/2017.
 * This class represent traits, such as strengh, agility, intteligence, etc...
 * Everything that can be valuated and modified by armor or stuff...
 */

public class Trait {

    protected long id;
    protected String label;
    protected int modifier;
    protected int value;

    public Trait(String label, int value) {
        setLabel(label);
        setModifier(0);
        setValue(value);
        this.id=-1;
    }

    public Trait setLabel(String label) {
        this.label = label.toUpperCase(Locale.ENGLISH);
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Trait setModifier(int modifier) {
        /*if (modifier >= 0)*/ this.modifier = modifier;
        //else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The max value for a trait should be positive (got %d)", modifier));
        return this;
    }

    public int getModifier() {
        return modifier;
    }

    public Trait setValue(int value) {
        if (value >= 0) this.value = /*Math.min(*/value/*, value)*/;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The value for a trait should be positive (got %d)", value));
        return this;
    }

    public int getValue() {
        return value;
    }

    public long getId(){ return this.id;}

    public void setId(long id){this.id=id;}
}
