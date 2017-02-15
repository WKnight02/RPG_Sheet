package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;

import java.util.Collection;
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
        long a;

        ContentValues values=new ContentValues();
        values.put(NAME, ch.getName());
        retVal=mDb.insert(TABLE_NAME, null, values);
        // Last insert id int id=mDb.query();

        Collection<Info> infos=ch.getAllInfos();
        for (Info i: infos) {
            ContentValues valuesInfo=new ContentValues();
            // TODO
            //valuesInfo.put(Character_InfoDB.IDInfo,1);
            //valuesInfo.put(Character_InfoDB.IDCharacter, mDb.);
            a=mDb.insert(Character_InfoDB.TABLE_NAME, null, valuesInfo);
            if(a==-1){
                retVal=-1;
            }
        }

        return retVal;
    }

    public int updateCharacter(int id, Character ch){
        ContentValues values = new ContentValues();
        values.put(NAME, ch.getName());
        long a=mDb.update(TABLE_NAME, values, ID+"="+id, null);

        return 1;
    }
}
