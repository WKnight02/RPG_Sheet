package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;
import org.uqac.android.projet.rpgsheet.models.Trait;
import org.uqac.android.projet.rpgsheet.models.Skill;

import java.util.ArrayList;
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
        long id;

        ContentValues values=new ContentValues();
        values.put(NAME, ch.getName());
        id=mDb.insert(TABLE_NAME, null, values);
        if(id==-1){
            return -1;
        }
        ch.setId(id);

        return 0;
    }

    public long updateCharacter(Character ch){
        long retVal;
        long id=ch.getId();
        ContentValues values = new ContentValues();
        values.put(NAME, ch.getName());
        retVal=mDb.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});

        return retVal;
    }

    public long insertInfo(Info info, Character ch){
        long retVal;
        long idCharacter=ch.getId();

        ContentValues valuesInfo=new ContentValues();
        valuesInfo.put(Character_InfoDB.IDCharacter, idCharacter);
        valuesInfo.put(Character_InfoDB.LABEL, info.getLabel());
        valuesInfo.put(Character_InfoDB.VALUE, info.getValue());
        retVal=mDb.insert(Character_InfoDB.TABLE_NAME, null, valuesInfo);
        if(retVal==-1){
            return -1;
        }
        info.setId(retVal);

        return 0;
    }

    public long insertTrait(Trait trait, Character ch) {
        long retVal;
        long idCharacter=ch.getId();

        ContentValues valuesInfo=new ContentValues();

        valuesInfo.put(Character_StatisticDB.IDCharacter, idCharacter);
        valuesInfo.put(Character_StatisticDB.LABEL, trait.getLabel());
        valuesInfo.put(Character_StatisticDB.MAXVALUE, trait.getMaxValue());
        valuesInfo.put(Character_StatisticDB.VALUE, trait.getValue());
        retVal=mDb.insert(Character_StatisticDB.TABLE_NAME, null, valuesInfo);

        if(retVal==-1){
            return -1;
        }

        trait.setId(retVal);

        return 0;
    }

    public long insertSkill(Skill skill, Character ch){
        long retVal;
        long idCharacter=ch.getId();

        ContentValues valuesInfo=new ContentValues();
        valuesInfo.put(Character_SkillDB.IDCharacter, idCharacter);
        valuesInfo.put(Character_SkillDB.LABEL, skill.getLabel());
        valuesInfo.put(Character_SkillDB.DESCRIPTION, skill.getDescription());
        retVal=mDb.insert(Character_SkillDB.TABLE_NAME, null, valuesInfo);
        if(retVal==-1){
            return -1;
        }
        skill.setId(retVal);

        return 0;
    }

    public long deleteCharacter(Character ch){
        return mDb.delete(TABLE_NAME, ID+"=?",new String[]{ch.getId()+""});
    }

    public Collection<Character> getAllCharacters(){
        Collection<Character> characters = new ArrayList<Character>();
        Cursor curs=mDb.query(TABLE_NAME, new String[]{ID, NAME},null,null,null,null,null);
        curs.moveToFirst();
        do {
            Character ch=new Character(curs.getString(1));
            ch.setId(curs.getLong(0));
            characters.add(ch);
        }while(curs.moveToNext());
        return characters;
    }

    public long getMaxId(){
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, null, null, null, null, ID, "1");
        if(curs.getCount()==0)
            return -1;
        return curs.getLong(0);
    }
}
