package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Story;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class StoryDB extends DBBase {
    public static final String TABLE_NAME = "Story";
    public static final String ID="idStory";
    public static final String TITLE= "title";
    public static final String LORE= "lore";

    public StoryDB(Context pContext) {
        super(pContext);
    }

    public long insertStory(Story story){
        open();
        long id;

        ContentValues values=new ContentValues();
        values.put(TITLE, story.getTitle());
        values.put(LORE, story.getLore());
        id=mDb.insert(TABLE_NAME, null, values);
        if(id==-1){
            close();
            return -1;
        }
        story.setId(id);
        close();
        return 0;
    }

    public long updateStory(Story story){
        open();
        long retVal;
        long id=story.getId();
        ContentValues values = new ContentValues();
        values.put(TITLE, story.getTitle());
        values.put(LORE, story.getLore());
        retVal=mDb.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});
        close();
        return retVal;
    }

    public long deleteStory(Story story){
        open();
        long retVal=mDb.delete(TABLE_NAME, ID+"=?",new String[]{story.getId()+""});
        close();
        return retVal;
    }

    public Collection<Story> getAllStories(){
        open();
        Collection<Story> stories = new ArrayList<Story>();
        Cursor curs=mDb.query(TABLE_NAME, new String[]{ID, TITLE},null,null,null,null,null);
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        do {
            Story story=new Story(curs.getString(1));
            story.setId(curs.getLong(0));
            stories.add(story);
        }while(curs.moveToNext());
        close();
        return stories;
    }

    public long getMaxId(){
        open();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, null, null, null, null, ID, "1");
        if(curs.getCount()==0){
            close();
            return -1;
        }
        long retVal=curs.getLong(0);
        close();
        return retVal;
    }

    public Story getStoryByTile(String name){
        open();
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID,TITLE,LORE}, "title='"+name+"'", null, null, null, null, "1");
        if(curs.getCount()==0) {
            curs.close();
            close();
            return null;
        }
        curs.moveToFirst();
        Story story=new Story(curs.getString(1));
        story.setId(curs.getLong(0));
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
        return story;
    }
}
