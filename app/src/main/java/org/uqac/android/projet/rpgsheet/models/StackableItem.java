package org.uqac.android.projet.rpgsheet.models;

import java.util.Locale;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class represent stackable items, such as arrows, potions, etc...
 * Items that you can stack.
 */

public class StackableItem extends Item {

    public StackableItem(String name) {
        super(name);
    }

    public StackableItem(String name, int quantity) {
        this(name);
        setQuantity(quantity);
    }

    public StackableItem setMaxQuantity(int maxQuantity) {
        if (maxQuantity > 0) this.maxQuantity = maxQuantity;
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Stackable max quantity should be at least 1 (got %d for %s)", maxQuantity, name));
        return this;
    }

    public StackableItem setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Stackable quantity should be positive (got %d for %s)", quantity, name));
        if (quantity > maxQuantity)
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Stackable max quantity exceeded (got %d > %d for %s)", quantity, maxQuantity, name));
        else this.quantity = quantity;
        return this;
    }

    public StackableItem addQuantity(int quantity) {
        return setQuantity(this.quantity + quantity);
    }

    public StackableItem removeQuantity(int quantity) {
        return addQuantity(-quantity);
    }
}
