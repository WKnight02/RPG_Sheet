package org.uqac.android.projet.rpgsheet.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by White_Knight02 on 10/02/2017.
 * This class simply represents an item that can be equiped
 * (not all items can be equiped... try equiping a coin...)
 */

public class EquipableItem extends Item {

    protected Map<String, Integer> modifiers;

    public EquipableItem(String name) {
        super(name);
        modifiers = new HashMap<String, Integer>();
    }

    public EquipableItem setModifier(String label, int value) {
        modifiers.put(label, value);
        return this;
    }

    public Map<String, Integer> getModifiers() {
        return modifiers;
    }
}
