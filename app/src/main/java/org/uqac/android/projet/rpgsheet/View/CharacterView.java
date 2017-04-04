package org.uqac.android.projet.rpgsheet.View;

//import android.app.FragmentTransaction;

import android.content.Context;
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
import org.uqac.android.projet.rpgsheet.Fragment.InfoFrag;
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
    private InfoFrag infoFrag;
    private DiceFrag diceFrag;
    private SkillsFrag skillsFrag;
    private TraitsFrag traitsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_view);
        final Context context = this;

        infoFrag = new InfoFrag();
        skillsFrag= new SkillsFrag();
        traitsFrag= new TraitsFrag();
        diceFrag= new DiceFrag();


        name=(String) getIntent().getExtras().get("name");

        this.tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        Log.d("name", name);

        tabHost.addTab(tabHost.newTabSpec("nameAndInfo").setIndicator(createTabView(this, "Infos")), InfoFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("skill").setIndicator(createTabView(this, "Skills")),SkillsFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("stats").setIndicator(createTabView(this, "Stats")), TraitsFrag.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("dice").setIndicator(createTabView(this, "Dice")), DiceFrag.class, bundle);

        /*
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
*/
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(android.R.id.tabcontent, infoFrag).commit();
        }

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
        transaction.replace(android.R.id.tabcontent, frag).addToBackStack(null);;
        transaction.commit();
    }

    public void Return(View view){
        /*
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);*/
        finish();
    }

    private static View createTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.onglet_view, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }
}
