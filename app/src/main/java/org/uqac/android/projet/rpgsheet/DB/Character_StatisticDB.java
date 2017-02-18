package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;

import org.uqac.android.projet.rpgsheet.models.Trait;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_StatisticDB extends DBBase {

    public static final String TABLE_NAME = "Character_Statistic";
    public static final String IDCharacter="idCharacter";
    public static final String IDTrait= "idStatistic";
    public static final String VALUE= "baseValue";
    public static final String MAXVALUE="modifier";
    public static final String LABEL= "label";

    public Character_StatisticDB(Context pContext) {
        super(pContext);
    }

    public long updateSkill(Trait trait){
        if(trait.getId()==-1){
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(VALUE, trait.getValue());
        values.put(LABEL, trait.getLabel());
        values.put(MAXVALUE, trait.getMaxValue());
        return mDb.update(TABLE_NAME, values, IDTrait+"="+trait.getId(), null);
    }

    public long deleteSkill(Trait trait){
        if(trait.getId()==-1){
            throw new IllegalArgumentException("A Statistic must be associated with a Character");
        }
        return mDb.delete(TABLE_NAME, IDTrait+"=?",new String[]{trait.getId()+""});
    }
}
