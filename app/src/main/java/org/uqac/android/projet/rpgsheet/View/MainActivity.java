package org.uqac.android.projet.rpgsheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.uqac.android.projet.rpgsheet.DB.DBBase;
import org.uqac.android.projet.rpgsheet.DB.DatabaseHandler;
import org.uqac.android.projet.rpgsheet.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.database_reset:
                DBBase db = new DBBase(this);

                db.open();
                db.getDbHandler().onUpgrade(db.getDb(), 0, 1);
                db.close();

                Toast.makeText(this, "Database Cleaned", Toast.LENGTH_SHORT).show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPlayerView(View view){
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);
    }

    public void loadGMView(View view){
        Intent intent = new Intent(this, StoriesView.class);
        startActivity(intent);
    }
}
