package org.uqac.android.projet.rpgsheet.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.uqac.android.projet.rpgsheet.Adapters.MonsterExpandableQuickAdapter;
import org.uqac.android.projet.rpgsheet.Adapters.MonsterQuickAdapter;
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

    private ArrayList<String> monsterNames;
    private Collection<Trait> traits;
    private Collection<Info> infos;
    private Collection<Skill> skills;
    private Story story;

    private StoryDB dbStory;

    private ExpandableListView monsterList;
    private EditText newMonsterName;
    private EditText loreText;
    private Button addMonster;

    private ArrayList<Monster> monsters;
    private MonsterExpandableQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_view);
        String title = (String)getIntent().getExtras().get("name");
        dbStory = new StoryDB(this);

        story = dbStory.getStoryByTile(title);

        monsterList = (ExpandableListView)findViewById(R.id.monstersExpandableListView);
        addMonster = (Button)findViewById(R.id.storyTabMonstersAddMonster);
        newMonsterName = (EditText)findViewById(R.id.storyTabMonstersNewName);

        loreText = (EditText)findViewById(R.id.storyTextML);
        loreText.setText(story.getLore());

        Story_MonsterDB dbMonters = new Story_MonsterDB(this);
        monsters = new ArrayList<Monster>();

        Collection<Monster> monstersCollection = dbMonters.getAllMonstersForStory(story);
        if (monstersCollection != null) {
            monsters.addAll(monstersCollection);
        }

        // Tabs
        TabHost.TabSpec spec;
        TabHost host = (TabHost)findViewById(R.id.storyTabs);
        host.setup();

        spec = host.newTabSpec("Story");
        spec.setContent(R.id.storyTabStory);
        spec.setIndicator(getString(R.string.story));
        host.addTab(spec);

        spec = host.newTabSpec("Monsters");
        spec.setContent(R.id.storyTabMonsters);
        spec.setIndicator(getString(R.string.monsters));
        host.addTab(spec);

        monsterList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                parent.expandGroup(groupPosition);
                return false;
            }
        });

        if(monsters != null) {

            adapter = new MonsterExpandableQuickAdapter(this, monsters);
            monsterList.setAdapter(adapter);

            /*
            monsterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(StoryView.this, MonsterView.class);
                    String name = monsterNames.get(position);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });//*/
        }
    }

    public void addMonster(View view) {
        Story_MonsterDB dbMonster = new Story_MonsterDB(this);
        long i = dbMonster.getMaxId();

        try {
            String name = newMonsterName.getText().toString();
            Log.d("New Monster!", name);
            Monster monster = new Monster(name);
            dbMonster.insertMonster(monster, story);
            story.addMonster(monster);

            monsters.add(monster);
            newMonsterName.setText("");
            InputMethodManager inputManager =
                    (InputMethodManager)getApplicationContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //*
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.onglets_story, menu);
        //*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.dice:
                Intent intent = new Intent(this, DiceRolling.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Return(View view){
        /*
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);*/
        finish();
    }

    @Override
    public void onPause() {
        // Mise à jour des données lorsque l'on met juste en pause l'activité.
        loreText = (EditText)findViewById(R.id.storyTextML);

        story.setLore(loreText.getText().toString());
        dbStory.updateStory(story);

        super.onPause();
    }
}
