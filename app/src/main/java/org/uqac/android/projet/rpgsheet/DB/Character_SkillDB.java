package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;

import org.uqac.android.projet.rpgsheet.models.Skill;

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

    public long updateSkill(Skill skill){
        if(skill.getId()==-1){
            throw new IllegalArgumentException("An Info must be associated with a Character");
        }
        ContentValues values=new ContentValues();
        values.put(DESCRIPTION, skill.getDescription());
        values.put(LABEL, skill.getLabel());
        return mDb.update(TABLE_NAME, values, IDSkill+"="+skill.getId(), null);
    }

    public long deleteSkill(Skill skill){
        if(skill.getId()==-1){
            throw new IllegalArgumentException("A Skill must be associated with a Character");
        }
        return mDb.delete(TABLE_NAME, IDSkill+"=?",new String[]{skill.getId()+""});
    }

}
