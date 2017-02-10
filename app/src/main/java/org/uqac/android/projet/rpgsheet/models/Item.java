package org.uqac.android.projet.rpgsheet.models;

import java.util.Locale;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class is the base for future items
 * (stackable, equipment, etc...)
 */

public abstract class Item {

    protected String name;
    protected String description;
    protected int maxQuantity = 1;
    protected int quantity = 1;

    public Item(String name) {
        setName(name);
    }

    public Item setName(String name) {
        if (name.isEmpty())
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Item name should not be empty... (%s)", name));
        else this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }
    public int getQuantity() {
        return quantity;
    }
}
