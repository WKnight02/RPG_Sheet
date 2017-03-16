package org.uqac.android.projet.rpgsheet.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
import org.uqac.android.projet.rpgsheet.DB.Character_InfoDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bruno.J on 16/03/2017.
 */
public class NamesAndInfoFrag extends Fragment{
    private Character ch;
    private CharacterDB dbCharacter;
    private String name;
    private Collection<Info> infos;
    private Context activity;

    private Character_InfoDB dbInfos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.name_infos_view, container, false);
        ListView listInfos=(ListView) view.findViewById(R.id.infos);

        name=(String) getArguments().getString("name");

        if((activity=getActivity())!=null) {
            dbCharacter = new CharacterDB(activity);
            dbInfos = new Character_InfoDB(activity);

            ch = dbCharacter.getCharacterByName(name);
            infos = dbInfos.getAllInfosForCharacter(ch);
            ArrayList<String> listInfoString= new ArrayList<String>();
            for(Info info : infos){
                listInfoString.add(info.toString());
            }

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listInfoString);
            listInfos.setAdapter(adapter);
        }
            return view;
    }
}
