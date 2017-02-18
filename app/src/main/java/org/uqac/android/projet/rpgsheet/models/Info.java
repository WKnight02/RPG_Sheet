package org.uqac.android.projet.rpgsheet.models;

import java.text.NumberFormat;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class represent any information about the character
 * (race, age, birthdate, etc... anything the user wants)
 */

public class Info {

    protected long id;
    protected String label;
    protected String value;

    public Info(String label) {
        setLabel(label);
    }

    public Info(String label, String value) {
        this(label);
        setValue(value);
        this.id=-1;
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
        return setValue(NumberFormat.getInstance().format(value));
    }

    public Info setValue(Object value) {
        return setValue(value.toString());
    }

    public String getValue() {
        return value;
    }

    public long getId(){ return this.id;}

    public void setId(long id){this.id=id;}
}
