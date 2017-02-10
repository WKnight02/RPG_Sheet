package org.uqac.android.projet.rpgsheet.models;

import java.util.Locale;

/**
 * Created by White_Knight02 on 09/02/2017.
 * This class represent traits, such as strengh, agility, intteligence, etc...
 * Everything that can be valuated and modified by armor or stuff...
 */

public class Trait {

    protected String label;
    protected int maxValue;
    protected int value;

    public Trait(String label) {

    }

    public Trait setLabel(String label) {
        this.label = label.toUpperCase(Locale.ENGLISH);
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Trait setMaxValue(int maxValue) {
        if (maxValue >= 0) this.maxValue = maxValue;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The max value for a trait should be positive (got %d)", maxValue));
        return this;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public Trait setValue(int value) {
        if (value >= 0) this.value = Math.min(this.maxValue, value);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The value for a trait should be positive (got %d)", value));
        return this;
    }

    public int getValue() {
        return value;
    }
}
