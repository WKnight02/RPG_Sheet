package org.uqac.android.projet.rpgsheet.models;

import java.text.NumberFormat;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class represent any information about the character
 * (race, age, birthdate, etc... anything the user wants)
 */

public class Info {

    protected String label;
    protected String value;

    public Info(String label) {
        setLabel(label);
    }

    public Info(String label, String value) {
        this(label);
        setValue(value);
    }

    public Info setLabel(String label) {
        if (label.isEmpty())
            throw new IllegalArgumentException("Info label should not be empty");
        else this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Info setValue(String value) {
        this.value = value;
        return this;
    }

    public Info setValue(double value) {
        this.value = NumberFormat.getInstance().format(value);
        return this;
    }

    public Info setValue(Object value) {
        this.value = value.toString();
        return this;
    }

    public String getValue() {
        return value;
    }
}
