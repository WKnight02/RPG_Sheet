package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.uqac.android.projet.rpgsheet.DB.StoryDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Story;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 10/02/2017.
 */
public class StoriesView extends ActionBarActivity {
    private  ArrayList<String> names;
    private StoryDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_master_view);
        ListView view=(ListView) findViewById(R.id.StoriesList);
        db= new StoryDB(this);
        final Collection<Story> stories=db.getAllStories();

        names=new ArrayList<>();
        for (Story story:stories){
            names.add(story.getTitle());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                names.get(position);
                setContentView(R.layout.story_view);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Return(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createStory(View view){
        Story story;
        long i;
        if((i=db.getMaxId())==-1){
            story=new Story("Story");
        }
        else{
            story=new Story("Story"+(i+1));
        }
        db.insertStory(story);
        recreate();
    }
}
