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
    /*private Character_InfoDB dbInfo;
    private Character_SkillDB dbSkill;
    private Character_StatisticDB dbStatistic;
    */

    public static final String TABLE_NAME = "Character";
    public static final String ID="idCharacter";
    public static final String NAME= "name";

    public CharacterDB(Context pContext) {
        super(pContext);/*
        dbInfo=new Character_InfoDB(pContext);
        dbSkill=new Character_SkillDB(pContext);
        dbStatistic=new Character_StatisticDB(pContext);
        */
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
        if(curs.getCount()==0){
            curs.close();
            return null;
        }
        curs.moveToFirst();
        do {
            Character ch=new Character(curs.getString(1));
            ch.setId(curs.getLong(0));
            characters.add(ch);
        }while(curs.moveToNext());
        curs.close();
        return characters;
    }

    public long getMaxId(){
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, null, null, null, null, ID, "1");
        if(curs.getCount()==0) {
            curs.close();
            return -1;
        }
        curs.close();
        return curs.getLong(0);
    }

    public Character getCharacterByName(String name){
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID,NAME}, "name="+name, null, null, null, null, "1");
        if(curs.getCount()==0) {
            curs.close();
            return null;
        }
        curs.moveToFirst();
        Character ch=new Character(curs.getString(1));
        ch.setId(curs.getLong(0));
        curs.close();

        Collection<Info> infos=getAllInfos(ch);
        for(Info info:infos){
            ch.addInfo(info);
        }

        Collection<Trait> traits=getAllStatistics(ch);
        for(Trait trait:traits){
            ch.addTrait(trait);
        }

        Collection<Skill> skills=getAllSkill(ch);
        for(Skill skill:skills){
            //ch.addSkill(skill);
        }

        return ch;
    }

    public Collection<Info> getAllInfos(Character ch){
        Collection<Info> infos=new ArrayList<Info>();
        Cursor curs= mDb.query(Character_InfoDB.TABLE_NAME, new String[]{Character_InfoDB.IDInfo,Character_InfoDB.LABEL, Character_InfoDB.VALUE}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            return null;
        }
        curs.moveToFirst();
        do {
            Info info=new Info(curs.getString(1), curs.getString(2));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        return infos;
    }

    public Collection<Trait> getAllStatistics(Character ch){
        Collection<Trait> infos=new ArrayList<Trait>();
        Cursor curs= mDb.query(Character_StatisticDB.TABLE_NAME, new String[]{Character_StatisticDB.IDTrait,Character_StatisticDB.LABEL, Character_StatisticDB.VALUE, Character_StatisticDB.MAXVALUE}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            return null;
        }
        curs.moveToFirst();
        do {
            Trait info=new Trait(curs.getString(1), curs.getInt(2));
            info.setMaxValue(curs.getInt(3));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        return infos;
    }

    public Collection<Skill> getAllSkill(Character ch){
        Collection<Skill> infos=new ArrayList<Skill>();
        Cursor curs= mDb.query(Character_SkillDB.TABLE_NAME, new String[]{Character_SkillDB.IDSkill,Character_SkillDB.LABEL, Character_SkillDB.DESCRIPTION}, "idCharacter="+ch.getId(), null, null, null, null, null);
        if(curs.getCount()==0) {
            curs.close();
            return null;
        }
        curs.moveToFirst();
        do {
            Skill info=new Skill(curs.getString(1), curs.getString(2));
            info.setId(curs.getLong(0));
            infos.add(info);
        }while(curs.moveToNext());
        return infos;
    }
}
