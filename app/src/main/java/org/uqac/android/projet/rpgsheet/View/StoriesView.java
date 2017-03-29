package org.uqac.android.projet.rpgsheet.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.uqac.android.projet.rpgsheet.DB.StoryDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Story;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 10/02/2017.
 */
public class StoriesView extends ActionBarActivity {

    private ArrayList<String> storyNames;
    private ArrayAdapter<String> adapter;
    private StoryDB dbStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_master_view);

        ListView view = (ListView)findViewById(R.id.StoriesList);
        registerForContextMenu(view);
        dbStory = new StoryDB(this);

        final Collection<Story> stories = dbStory.getAllStories();

        storyNames = new ArrayList<>();
        if(stories != null){
            for (Story story : stories){
                storyNames.add(story.getTitle());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storyNames);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(StoriesView.this, StoryView.class);
                    String name = storyNames.get(position);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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
        /* Old
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //*/
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.StoriesList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.object_selected, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String name = adapter.getItem(info.position);

        switch (item.getItemId()) {

            case R.id.delete:
                deleteStory(name);
                return true;

            case R.id.rename:
                renameStory(name);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void createStory(View view){
        /* OLD
        long i = dbStory.getMaxId();
        Story story;

        if(i == -1){
            story = new Story("Story");
        }
        else{
            story = new Story("Story"+(i+1));
        }

        dbStory.insertStory(story);
        recreate();
        //*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the name of your Story:");

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
                    Story story = new Story(name);
                    dbStory.insertStory(story);
                    recreate();

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

    private void deleteStory(String name) {
        Story story = dbStory.getStoryByTile(name);
        if(story != null) {
            dbStory.deleteStory(story);
            recreate();
        }
    }

    public void renameStory(final String oldName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set the name of your Story:");

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

                Story story = dbStory.getStoryByTile(oldName);

                try {
                    story.setTitle(name);
                    dbStory.updateStory(story);
                    recreate();

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
}
