package org.uqac.android.projet.rpgsheet.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_infos_view, container, false);
        ListView listInfos = (ListView) view.findViewById(R.id.infos);

        //Button add new info
        Button button=(Button) view.findViewById(R.id.newInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Set the label and the information you want to create:");

                    final EditText labelInput = new EditText(getActivity());
                    labelInput.setInputType(InputType.TYPE_CLASS_TEXT);

                    final EditText infoInput = new EditText(getActivity());
                    infoInput.setInputType(InputType.TYPE_CLASS_TEXT);

                    final LinearLayout ly=new LinearLayout(getActivity());
                    ly.setOrientation(LinearLayout.HORIZONTAL);
                    ly.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                    ly.addView(labelInput);
                    ly.addView(infoInput);
                    builder.setView(ly);


                    // Set up the buttons
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String label = labelInput.getText().toString();
                            String info=infoInput.getText().toString();

                            Info newInfo=new Info(label, info);
                            dbInfos.insertInfo(newInfo, ch);
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
        });

        if (getArguments() == null) {
            return view;
        }

        name = (String) getArguments().getString("name");

        if ((activity = getActivity()) != null) {
            dbCharacter = new CharacterDB(activity);
            dbInfos = new Character_InfoDB(activity);

            ch = dbCharacter.getCharacterByName(name);
            infos = dbInfos.getAllInfosForCharacter(ch);
            ArrayList<String> listInfoString = new ArrayList<String>();

            if (infos != null) {
                for (Info info : infos) {
                    listInfoString.add(info.toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listInfoString);
                listInfos.setAdapter(adapter);
            }
        }

        return view;
    }
}
