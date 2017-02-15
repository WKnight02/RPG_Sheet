package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;
import org.uqac.android.projet.rpgsheet.models.Trait;
/**
 * Created by Bruno.J on 14/02/2017.
 */

public class CharacterDB extends DBBase {
    public static final String TABLE_NAME = "Character";
    public static final String ID="idCharacter";
    public static final String NAME= "name";

    public CharacterDB(Context pContext) {
        super(pContext);
    }

    public long insertCharacter(Character ch){
        long retVal=1;
        long id;

        ContentValues values=new ContentValues();
        values.put(NAME, ch.getName());
        id=mDb.insert(TABLE_NAME, null, values);
        if(id==-1){
            return -1;
        }
        ch.setId(id);
/*
        Collection<Info> infos=ch.getAllInfos();
        for (Info i: infos) {
            retVal=insertInfo(i, id);
            if(retVal==-1){
                return -1;
            }
        }

        Collection<Trait> traits=ch.getAllTraits();
        for(Trait t: traits){
            retVal=insertTrait(t, id);
            if(retVal==-1) {
                return -1;
            }
        }
*/
        return 0;
    }

    public int updateCharacter(Character ch){
        long retVal;
        long id=ch.getId();
        ContentValues values = new ContentValues();
        values.put(NAME, ch.getName());
        retVal=mDb.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});
        if(retVal==-1) {
            return -1;
        }
        return 0;
    }

    public long insertInfo(Info info, long idCharacter){
        long idInfo;
        long retVal;

        ContentValues valuesInfo=new ContentValues();
        idInfo=info.getId();
        valuesInfo.put(Character_InfoDB.IDInfo,idInfo);
        valuesInfo.put(Character_InfoDB.IDCharacter, idCharacter);
        retVal=mDb.insert(Character_InfoDB.TABLE_NAME, null, valuesInfo);

        return retVal;
    }

    public long insertTrait(Trait trait, long idCharacter) {
        long idTrait;
        long retVal;

        ContentValues valuesInfo=new ContentValues();
        idTrait=trait.getId();
        valuesInfo.put(Character_StatisticDB.IDTrait,idTrait);
        valuesInfo.put(Character_StatisticDB.IDCharacter, idCharacter);
        retVal=mDb.insert(Character_StatisticDB.TABLE_NAME, null, valuesInfo);

        return retVal;
    }

    public long deleteCharacter(Character ch){
        return mDb.delete(TABLE_NAME, ID+"=?",new String[]{ch.getId()+""});
    }
}
