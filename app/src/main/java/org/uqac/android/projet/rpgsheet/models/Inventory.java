package org.uqac.android.projet.rpgsheet.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class manages the character inventory.
 * (equipment, random items, etc...)
 */

public class Inventory implements Iterable {

    protected Set<Item> items;
    protected Set<Item> equiped;

    public Inventory() {
        items = new HashSet<Item>();
        equiped = new HashSet<Item>();
    }

    public Inventory addItem(Item item) {
        return addItem(item, false);
    }

    public Inventory addItem(Item item, boolean equip) {
        items.add(item);
        if (equip) equiped.add(item);
        return this;
    }

    public Inventory equipItem(Item item) {
        if (items.contains(item)) equiped.add(item);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This item was not in the inventory, couldn't equip it (%s)", item.getName()));
        return this;
    }

    public Inventory dropItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            if (equiped.contains(item)) equiped.remove(item);
        } else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This item was not in your possession to begin with (%s)", item.getName()));
        return this;
    }

    public Collection<Item> getAllItems() {
        return items;
    }

    public Collection<Item> getEquipedItems() {
        return equiped;
    }

    public boolean isItemEquiped(Item item) {
        return equiped.contains(item);
    }

    /**
     * Makes the Inventory iterable
     * @return items.iterator()
     */
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
