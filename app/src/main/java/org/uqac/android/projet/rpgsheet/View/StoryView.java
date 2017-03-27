package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.uqac.android.projet.rpgsheet.DB.StoryDB;
import org.uqac.android.projet.rpgsheet.DB.Story_MonsterDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Info;
import org.uqac.android.projet.rpgsheet.models.Monster;
import org.uqac.android.projet.rpgsheet.models.Skill;
import org.uqac.android.projet.rpgsheet.models.Story;
import org.uqac.android.projet.rpgsheet.models.Trait;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 15/03/2017.
 */
public class StoryView extends ActionBarActivity{

    private ArrayList<String> names;
    private Collection<Trait> traits;
    private Collection<Info> infos;
    private Collection<Skill> skills;
    private Story story;

    private StoryDB dbStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_infos_view);
        String title = (String)getIntent().getExtras().get("name");
        dbStory = new StoryDB(this);

        story = dbStory.getStoryByTile(title);

        ListView view=(ListView) findViewById(R.id.CharactersList);
        Story_MonsterDB dbMonters = new Story_MonsterDB(this);
        final Collection<Monster> monsters = dbMonters.getAllMonstersForStory(story);

        names = new ArrayList<>();
        if(monsters != null) {
            for (Monster monster : monsters) {
                names.add(monster.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(StoryView.this, MonsterView.class);
                    String name = names.get(position);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.onglets_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.monsters:
                setContentView(R.layout.monsters_view);
                return true;
            case R.id.lore:
                setContentView(R.layout.lore_view);
                return true;
            case R.id.dice:
                setContentView(R.layout.dice_view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Return(View view){
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);
    }
}
