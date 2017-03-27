package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 10/02/2017.
 */

public class CharactersView extends ActionBarActivity {

    private  ArrayList<String> names;
    private CharacterDB dbCharacter;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characters_view);

        ListView view=(ListView) findViewById(R.id.CharactersList);
        dbCharacter=new CharacterDB(this);
        final Collection<Character> characters=dbCharacter.getAllCharacters();

        names=new ArrayList<>();
        if(characters!=null) {
            for (Character ch : characters) {
                names.add(ch.getName());
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(CharactersView.this, CharacterView.class);
                    String name=adapter.getItem(position);
                    Log.d("name", name);

                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.object_selected, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        String name=adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.delete:
                deleteCharacter(name);
                return true;
            case R.id.rename:
                //alertDialog
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteCharacter(String name) {
        Character ch= dbCharacter.getCharacterByName(name);
        if(ch!=null){
            dbCharacter.deleteCharacter(ch);
        }
    }

    public void Return(View view){
        /* Old
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //*/
        finish();
    }

    private void renameCharacter(String newName, String name){

    }

    public void createCharacter(View view){
        Character ch;
        long i;
        if((i=dbCharacter.getMaxId())==-1){
            ch=new Character("Character");
        }
        else{
            ch=new Character("Character"+(i+1));
        }
        dbCharacter.insertCharacter(ch);
        recreate();
    }
}
