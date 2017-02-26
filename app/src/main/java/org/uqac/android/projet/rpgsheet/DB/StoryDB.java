package org.uqac.android.projet.rpgsheet.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Monster;
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
        long id;

        ContentValues values=new ContentValues();
        values.put(TITLE, story.getTitle());
        values.put(LORE, story.getLore());
        id=mDb.insert(TABLE_NAME, null, values);
        if(id==-1){
            return -1;
        }
        story.setId(id);

        return 0;
    }

    public long updateStory(Story story){
        long retVal;
        long id=story.getId();
        ContentValues values = new ContentValues();
        values.put(TITLE, story.getTitle());
        values.put(LORE, story.getLore());
        retVal=mDb.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});

        return retVal;
    }

    public long insertMonster(Monster m, Story story) {
        long retVal;
        long idStory=story.getId();

        ContentValues values=new ContentValues();

        values.put(Story_MonsterDB.IDStory, idStory);
        values.put(Story_MonsterDB.NAME, m.getName());
        retVal=mDb.insert(Story_MonsterDB.TABLE_NAME, null, values);

        if(retVal==-1){
            return -1;
        }

        m.setId(retVal);

        return 0;
    }

    public long deleteStory(Story story){
        return mDb.delete(TABLE_NAME, ID+"=?",new String[]{story.getId()+""});
    }

    public Collection<Story> getAllStories(){
        Collection<Story> stories = new ArrayList<Story>();
        Cursor curs=mDb.query(TABLE_NAME, new String[]{ID, NAME},null,null,null,null,null);
        curs.moveToFirst();
        do {
            Story story=new Story(curs.getString(1));
            story.setId(curs.getLong(0));
            stories.add(story);
        }while(curs.moveToNext());
        return stories;
    }

    public long getMaxId(){
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, null, null, null, null, ID, "1");
        if(curs.getCount()==0)
            return -1;
        return curs.getLong(0);
    }
}
