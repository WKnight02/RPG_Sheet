package org.uqac.android.projet.rpgsheet.View;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;

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

    private TabHost tabHost;

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

        this.tabHost = (TabHost) findViewById(android.R.id.tabhost);

        tabHost.addTab(tabHost.newTabSpec("nameAndInfo").setIndicator("Name and Informations").setContent(new Intent().setClass(this, NamesAndInfoFrag.class)));
        tabHost.addTab(tabHost.newTabSpec("skill").setIndicator("Skills").setContent(new Intent().setClass(this, SkillsFrag.class)));
        tabHost.addTab(tabHost.newTabSpec("trait").setIndicator("Traits").setContent(new Intent().setClass(this, TraitsFrag.class)));
        tabHost.addTab(tabHost.newTabSpec("dice").setIndicator("Dice").setContent(new Intent().setClass(this, DiceFrag.class)));

        namesAndInfoFrag = new NamesAndInfoFrag();
        diceFrag= new DiceFrag();
        skillsFrag= new SkillsFrag();
        traitsFrag= new TraitsFrag();

        if (findViewById(android.R.id.tabcontent) != null) {

            if (savedInstanceState != null) {
                return;
            }
            replaceFragment(namesAndInfoFrag);
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
        Bundle bundle=new Bundle();
        bundle.putString("name", name);
        frag.setArguments(bundle);
        transaction=getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.tabcontent, frag);
        transaction.commit();
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.nameAndInfo:
                replaceFragment(namesAndInfoFrag);
                break;
            case R.id.skills:
                replaceFragment(skillsFrag);
                break;
            case R.id.traits:
                replaceFragment(traitsFrag);
                break;
            case R.id.dice:
                replaceFragment(diceFrag);
                break;
        }
    }

    public void Return(View view){
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);
    }
}
