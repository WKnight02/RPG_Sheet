package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Trait;

import java.util.ArrayList;
import java.util.Collection;

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

    public long insertTrait(Trait trait, Character ch) {
        open();
        long retVal;
        long idCharacter=ch.getId();

        ContentValues valuesInfo=new ContentValues();

        valuesInfo.put(IDCharacter, idCharacter);
        valuesInfo.put(LABEL, trait.getLabel());
        valuesInfo.put(MAXVALUE, trait.getMaxValue());
        valuesInfo.put(VALUE, trait.getValue());
        retVal=mDb.insert(TABLE_NAME, null, valuesInfo);

        if(retVal==-1){
            close();
            return -1;
        }

        trait.setId(retVal);
        close();
        return 0;
    }

    public long updateSkill(Trait trait){
        open();
        if(trait.getId()==-1){
            close();
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(VALUE, trait.getValue());
        values.put(LABEL, trait.getLabel());
        values.put(MAXVALUE, trait.getMaxValue());
        long retVal=mDb.update(TABLE_NAME, values, IDTrait+"="+trait.getId(), null);
        close();
        return retVal;
    }

    public long deleteSkill(Trait trait){
        open();
        if(trait.getId()==-1){
            close();
            throw new IllegalArgumentException("A Statistic must be associated with a Character");
        }
        long retVal=mDb.delete(TABLE_NAME, IDTrait+"=?",new String[]{trait.getId()+""});
        close();
        return retVal;
    }

    public Collection<Trait> getAllStatisticsForCharacter(Character ch){
        open();
        Collection<Trait> infos=new ArrayList<Trait>();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{IDTrait,LABEL,VALUE,MAXVALUE}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        do {
            Trait info=new Trait(curs.getString(1), curs.getInt(2));
            info.setMaxValue(curs.getInt(3));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        close();
        return infos;
    }
}
