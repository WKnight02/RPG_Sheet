package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
import org.uqac.android.projet.rpgsheet.DB.Character_InfoDB;
import org.uqac.android.projet.rpgsheet.DB.Character_SkillDB;
import org.uqac.android.projet.rpgsheet.DB.Character_StatisticDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;
import org.uqac.android.projet.rpgsheet.models.Inventory;
import org.uqac.android.projet.rpgsheet.models.Skill;
import org.uqac.android.projet.rpgsheet.models.Trait;

import java.util.Collection;


/**
 * Created by Bruno.J on 15/02/2017.
 */
public class CharacterView extends ActionBarActivity{

    private CharacterDB dbCharacter;
    private Character_InfoDB dbInfos;
    private Character_SkillDB dbSkills;
    private Character_StatisticDB dbTraits;

    private Character ch;
    private Collection<Trait> traits;
    private Collection<Info> infos;
    private Collection<Skill> skills;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_infos_view);

        dbCharacter=new CharacterDB(this);
        dbInfos=new Character_InfoDB(this);
        dbSkills=new Character_SkillDB(this);
        dbTraits=new Character_StatisticDB(this);
/*
        ArrayList<String> todoItems=new ArrayList<String>();

        Cursor c = dbInfos.showAllTables();
        if (c.moveToFirst())
        {
            do{
                todoItems.add(c.getString(0));

            }while (c.moveToNext());
        }
        if (todoItems.size() >= 0)
        {
            for (int i=0; i<todoItems.size(); i++)
            {
                Log.d("TODOItems(" + i + ")", todoItems.get(i) + "");

            }

        }
        c.close();
        dbInfos.close();
*/
        String name= (String) getIntent().getExtras().get("name");
        ch=dbCharacter.getCharacterByName(name);

        infos=dbInfos.getAllInfosForCharacter(ch);
        traits=dbTraits.getAllStatisticsForCharacter(ch);
        skills=dbSkills.getAllSkillsForCharacter(ch);


        Toast.makeText(this,infos+"", Toast.LENGTH_LONG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.onglets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.nameAndInfo:
                setContentView(R.layout.name_infos_view);
                return true;
            case R.id.trait:
                setContentView(R.layout.traits_view);
                return true;
            case R.id.skill:
                setContentView(R.layout.skills_view);
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
