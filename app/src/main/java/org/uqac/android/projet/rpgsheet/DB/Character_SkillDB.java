package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Skill;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_SkillDB extends DBBase {

    public static final String TABLE_NAME = "Character_Skill";
    public static final String IDCharacter="idCharacter";
    public static final String IDSkill= "idSkill";
    public static final String DESCRIPTION= "description";
    public static final String LABEL= "label";

    public Character_SkillDB(Context pContext) {
        super(pContext);
    }

    public long insertSkill(Skill skill, Character ch){
        open();
        long retVal;
        long idCharacter=ch.getId();

        ContentValues valuesInfo=new ContentValues();
        valuesInfo.put(IDCharacter, idCharacter);
        valuesInfo.put(LABEL, skill.getLabel());
        valuesInfo.put(DESCRIPTION, skill.getDescription());
        retVal=mDb.insert(TABLE_NAME, null, valuesInfo);
        if(retVal==-1){
            close();
            return -1;
        }
        skill.setId(retVal);
        close();
        return 0;
    }

    public long updateSkill(Skill skill){
        open();
        if(skill.getId()==-1){
            close();
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(DESCRIPTION, skill.getDescription());
        values.put(LABEL, skill.getLabel());
        long retVal=mDb.update(TABLE_NAME, values, IDSkill+"="+skill.getId(), null);
        close();
        return retVal;
    }

    public long deleteSkill(Skill skill){
        open();
        if(skill.getId()==-1){
            close();
            throw new IllegalArgumentException("A Skill must be associated with a Character");
        }
        long retVal=mDb.delete(TABLE_NAME, IDSkill+"=?",new String[]{skill.getId()+""});
        close();
        return retVal;
    }

    public Collection<Skill> getAllSkillsForCharacter(Character ch){
        open();
        Collection<Skill> infos=new ArrayList<Skill>();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{IDSkill,LABEL,DESCRIPTION}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        do {
            Skill info=new Skill(curs.getString(1), curs.getString(2));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        close();
        return infos;
    }
}
