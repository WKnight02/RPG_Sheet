package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Character;

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
        open();
        ContentValues values=new ContentValues();
        values.put(NAME, ch.getName());
        id=mDb.insert(TABLE_NAME, null, values);
        if(id==-1){
            close();
            return -1;
        }
        ch.setId(id);

        close();
        return 0;
    }

    public long updateCharacter(Character ch){
        open();
        long retVal;
        long id=ch.getId();
        ContentValues values = new ContentValues();
        values.put(NAME, ch.getName());
        retVal=mDb.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});

        close();
        return retVal;
    }

    public long deleteCharacter(Character ch){
        open();
        long retVal=mDb.delete(TABLE_NAME, ID+"=?",new String[]{ch.getId()+""});
        close();
        return retVal;
    }

    public Collection<Character> getAllCharacters(){
        open();
        Collection<Character> characters = new ArrayList<Character>();
        Cursor curs=mDb.query(TABLE_NAME, new String[]{ID, NAME},null,null,null,null,null);
        if(curs.getCount()==0){
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        do {
            Character ch=new Character(curs.getString(1));
            ch.setId(curs.getLong(0));
            characters.add(ch);
        }while(curs.moveToNext());
        curs.close();
        close();
        return characters;
    }

    public long getMaxId(){
        open();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, null, null, null, null, ID, "1");
        if(curs.getCount()==0) {
            curs.close();
            close();
            return -1;
        }
        curs.moveToFirst();
        long idMax=curs.getLong(0);
        curs.close();
        close();
        return idMax;
    }

    public Character getCharacterByName(String name){
        open();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID,NAME}, "name='"+name+"'", null, null, null, null, "1");
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        Character ch=new Character(curs.getString(1));
        ch.setId(curs.getLong(0));
        curs.close();
        close();
        /*
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
           */
        return ch;
    }
}
