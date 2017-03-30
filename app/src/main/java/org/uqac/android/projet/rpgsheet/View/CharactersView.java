package org.uqac.android.projet.rpgsheet.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Bruno.J on 10/02/2017.
 */

public class CharactersView extends ActionBarActivity {

    private ArrayList<String> names;
    private CharacterDB dbCharacter;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characters_view);

        listView = (ListView) findViewById(R.id.CharactersList);
        registerForContextMenu(listView);

        dbCharacter = new CharacterDB(this);
        final Collection<Character> characters = dbCharacter.getAllCharacters();

        names = new ArrayList<>();
        if(characters != null) {
            for (Character ch : characters) {
                names.add(ch.getName());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(CharactersView.this, CharacterView.class);
                    String name = adapter.getItem(position);
                    Log.d("name", name);

                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.CharactersList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.object_selected, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        String name = adapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.delete:
                deleteCharacter(name);
                return true;

            case R.id.rename:
                renameCharacter(name);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteCharacter(String name) {
        Character ch = dbCharacter.getCharacterByName(name);
        if(ch != null) {
            dbCharacter.deleteCharacter(ch);
            reloadAllData();
            //recreate();
        }
    }

    public void Return(View view){
        /* Old
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //*/
        finish();
    }

    public void createCharacter(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the name of your Character:");

        final EditText nameInput = new EditText(this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nameInput.setHint(R.string.Name);

        builder.setView(nameInput);
        final Context context = this;

        // Set up the buttons
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();

                try {
                    Character ch = new Character(name);
                    dbCharacter.insertCharacter(ch);
                    //recreate();
                    reloadAllData();
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void renameCharacter(final String oldName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the name of your Character:");

        final EditText nameInput = new EditText(this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nameInput.setHint(R.string.Name);

        builder.setView(nameInput);
        final Context context = this;

        // Set up the buttons
        builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();

                Character ch = dbCharacter.getCharacterByName(oldName);

                try {
                    ch.setName(name);
                    dbCharacter.updateCharacter(ch);
                    //recreate();
                    reloadAllData();

                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void reloadAllData(){
        // get new modified random data
        List<Character> characters= (List<Character>) dbCharacter.getAllCharacters();
        if(characters != null) {
            for (Character ch : characters) {
                names.add(ch.getName());
            }
            // update data in our adapter
            adapter.clear();
            if(names!=null)
                adapter.addAll(names);
            // fire the event
            adapter.notifyDataSetChanged();
        }

    }
}
