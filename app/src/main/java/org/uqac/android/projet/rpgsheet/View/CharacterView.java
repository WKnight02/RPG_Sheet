package org.uqac.android.projet.rpgsheet.View;

//import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.Fragment.DiceFrag;
import org.uqac.android.projet.rpgsheet.Fragment.NamesAndInfoFrag;
import org.uqac.android.projet.rpgsheet.Fragment.SkillsFrag;
import org.uqac.android.projet.rpgsheet.Fragment.TraitsFrag;
import org.uqac.android.projet.rpgsheet.R;

/**
 * Created by Bruno.J on 15/02/2017.
 */
public class CharacterView extends FragmentActivity {

    private String name;

    private FragmentTabHost tabHost;
    private FragmentTransaction transaction;
    private NamesAndInfoFrag namesAndInfoFrag;
    private DiceFrag diceFrag;
    private SkillsFrag skillsFrag;
    private TraitsFrag traitsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_view);

        name=(String) getIntent().getExtras().get("name");

        namesAndInfoFrag = new NamesAndInfoFrag();
        diceFrag= new DiceFrag();
        skillsFrag= new SkillsFrag();
        traitsFrag= new TraitsFrag();

        this.tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        Log.d("name", name);

        tabHost.addTab(tabHost.newTabSpec("nameAndInfo").setIndicator(createTabView(this, "Infos")), NamesAndInfoFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("skill").setIndicator(createTabView(this, "Skills")),SkillsFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("trait").setIndicator(createTabView(this, "Traits")), TraitsFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("dice").setIndicator(createTabView(this, "Dice")), DiceFrag.class, bundle);

        tabHost.setOnTabChangedListener(new FragmentTabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String s) {
                Log.d("current tab", "Current tab has changed.");
                switch (tabHost.getCurrentTab()){
                    case 0:
                        replaceFragment(namesAndInfoFrag);
                        break;
                    case 1:
                        replaceFragment(skillsFrag);
                        break;
                    case 2:
                        replaceFragment(traitsFrag);
                        break;
                    case 3:
                        replaceFragment(diceFrag);
                        break;
                }
            }
        });

        /*
        if (findViewById(android.R.id.tabcontent) != null) {

            if (savedInstanceState != null) {
                return;
            }

            replaceFragment(namesAndInfoFrag);
        }*/

        tabHost.setCurrentTab(0);
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
*/
    }


    public void replaceFragment(Fragment frag){
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.tabcontent, frag);
        transaction.commit();
    }

    public void Return(View view){
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);
    }

    private static View createTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.onglet_view, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }
}
