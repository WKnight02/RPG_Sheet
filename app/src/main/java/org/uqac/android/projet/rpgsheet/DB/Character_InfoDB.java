package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_InfoDB extends DBBase {
    public static final String TABLE_NAME = "Character_Info";
    public static final String IDCharacter="idCharacter";
    public static final String IDInfo= "idInfo";
    public static final String DESCRIPTION= "description";
    public static final String LABEL= "label";

    public Character_InfoDB(Context pContext) {
        super(pContext);
    }

    public long insertInfo(Info info, Character ch){
        long retVal;
        long idCharacter=ch.getId();
        open();

        ContentValues valuesInfo=new ContentValues();
        valuesInfo.put(IDCharacter, idCharacter);
        valuesInfo.put(LABEL, info.getLabel());
        valuesInfo.put(DESCRIPTION, info.getValue());
        retVal=mDb.insert(TABLE_NAME, null, valuesInfo);
        if(retVal==-1){
            close();
            return -1;
        }
        info.setId(retVal);

        close();
        return 0;
    }

    public long updateInfo(Info info){
        open();
        if(info.getId()==-1){
            close();
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(LABEL, info.getLabel());
        values.put(DESCRIPTION, info.getValue());

        long retVal=mDb.update(TABLE_NAME, values, IDInfo+"="+info.getId(), null);
        close();
        return retVal;
    }

    public long deleteInfo(Info info){
        open();
        if(info.getId()==-1){
            close();
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        long retVal=mDb.delete(TABLE_NAME, IDInfo+"=?",new String[]{info.getId()+""});
        close();
        return retVal;
    }

    public Collection<Info> getAllInfosForCharacter(Character ch){
        open();
        Collection<Info> infos=new ArrayList<Info>();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{IDInfo, LABEL, DESCRIPTION}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        do {
            Info info=new Info(curs.getString(1), curs.getString(2));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        close();
        return infos;
    }
}
