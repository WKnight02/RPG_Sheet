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
    protected HashMap<String, Trait> traits;
    protected HashMap<String, Info> infos;

    public Monster(String name) {
        traits = new HashMap<String, Trait>();
        infos = new HashMap<String, Info>();
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

    /**
     * Adds a trait to the character
     */
    public Monster addTrait(Trait trait) {
        String label = trait.getLabel();
        if (traits.containsKey(label))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait seems to already exists... (%s)", label));
        else traits.put(label, trait);
        return this;
    }

    public Trait getTrait(String label) {
        if (traits.containsKey(label)) return traits.get(label);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This trait doesn't seem to exists... (%s)", label));
    }

    public Collection<Trait> getAllTraits() {
        return traits.values();
    }

    /**
     * Adds an info to the character
     */
    public Monster addInfo(Info info) {
        String label = info.getLabel();
        if (traits.containsKey(label))
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This info seems to already exists... (%s)", label));
        else infos.put(label, info);
        return this;
    }

    public Info getInfo(String label) {
        if (infos.containsKey(label)) return infos.get(label);
        else throw new IllegalArgumentException(String.format(Locale.ENGLISH, "This info doesn't seem to exists... (%s)", label));
    }

    public Collection<Info> getAllInfos() {
        return infos.values();
    }

    public long getId(){ return this.id;}

    public void setId(long id){this.id=id;}

    public String toString(){
        return "Name: "+this.name;
    }
}
