package org.uqac.android.projet.rpgsheet.models;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class is the base for future items
 * (stackable, equipment, etc...)
 */

public abstract class Item {

    protected String name;
    protected String description;
    protected int quantity = 1;

    public Item(String name) {
        setName(name);
    }

    public Item setName(String name) {
        if (name.isEmpty())
            throw new IllegalArgumentException("Item name should not be empty...");
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

    public int getQuantity() {
        return quantity;
    }
}
