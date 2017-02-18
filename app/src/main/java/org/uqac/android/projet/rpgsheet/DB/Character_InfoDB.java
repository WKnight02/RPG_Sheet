package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import org.uqac.android.projet.rpgsheet.models.Info;


/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_InfoDB extends DBBase {
    public static final String TABLE_NAME = "Character_Info";
    public static final String IDCharacter="idCharacter";
    public static final String IDInfo= "idInfo";
    public static final String VALUE= "value";
    public static final String LABEL= "description";

    public Character_InfoDB(Context pContext) {
        super(pContext);
    }

    public long updateInfo(Info info){
        if(info.getId()==-1){
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(LABEL, info.getLabel());
        values.put(VALUE, info.getValue());
        return mDb.update(TABLE_NAME, values, IDInfo+"="+info.getId(), null);
    }

    public long deleteInfo(Info info){
        if(info.getId()==-1){
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        return mDb.delete(TABLE_NAME, IDInfo+"=?",new String[]{info.getId()+""});
    }
}
