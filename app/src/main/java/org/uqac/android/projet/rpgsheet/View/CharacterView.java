package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
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

    private Character ch;
    private Collection<Trait> traits;
    private Collection<Info> infos;
    private Collection<Skill> skills;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_infos_view);

        String name= (String) getIntent().getExtras().get("name");
        ch=dbCharacter.getCharacterByName(name);
        infos=ch.getAllInfos();
        traits=ch.getAllTraits();
        //skills=ch.getAllSkills();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Return(View view){
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);
    }
}
