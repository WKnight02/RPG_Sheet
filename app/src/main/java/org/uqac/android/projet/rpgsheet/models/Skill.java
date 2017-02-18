package org.uqac.android.projet.rpgsheet.models;

/**
 * Created by Bruno.J on 17/02/2017.
 */

public class Skill {

    protected long id;
    protected String label;
    protected String description;

    public Skill(String label, String description){
        setLabel(label);
        this.description=description;
        this.id=-1;
    }

    public long getId(){
        return this.id;
    }

    public String getLabel(){
        return this.label;
    }

    public String getDescription(){
        return this.description;
    }

    public void setId(long id){
        this.id=id;
    }

    public void setLabel(String label){
        if(label.isEmpty()){
            throw new IllegalArgumentException("Label can't be empty");
        }
        this.label=label;
    }

    public void setDescription(String description){
        this.description=description;
    }
}
