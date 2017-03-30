package org.uqac.android.projet.rpgsheet.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.DB.CharacterDB;
import org.uqac.android.projet.rpgsheet.DB.Character_InfoDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Info;

import java.util.List;

/**
 * Created by Bruno.J on 16/03/2017.
 */
public class InfoFrag extends Fragment{
    private Character ch;
    private CharacterDB dbCharacter;
    private Character_InfoDB dbInfos;
    private String name;
    private Context activity;
    private CustomAdapter adapter;
    private ListView listInfos;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infos_view, container, false);
        listInfos = (ListView) view.findViewById(R.id.infos);

        if (getArguments() == null ||(activity = getActivity()) == null) {
            return view;
        }

        name = (String) getArguments().getString("name");

        Log.d("name", name);

        dbCharacter = new CharacterDB(activity);
        dbInfos = new Character_InfoDB(activity);

        ch = dbCharacter.getCharacterByName(name);
        List<Info> infos = (List<Info>) dbInfos.getAllInfosForCharacter(ch);

        if (infos != null) {
            Log.d("infos", infos.toString());
            adapter = new CustomAdapter(activity, android.R.layout.simple_list_item_1, infos);
            listInfos.setAdapter(adapter);


            //Modifier valeurs
            listInfos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    List<Info> infos;
                    Log.d("click?","click");
                    if((infos=adapter.getInfos())!=null) {
                        final Info i = infos.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Edit the label and the information:");

                        final EditText labelInput = new EditText(getActivity());
                        labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        labelInput.setHint(R.string.label);
                        labelInput.setText(i.getLabel().substring(0, i.getLabel().length() - 3));

                        final EditText infoInput = new EditText(getActivity());
                        infoInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        infoInput.setHint(R.string.description);
                        infoInput.setText(i.getValue());

                        final LinearLayout ly = new LinearLayout(getActivity());
                        ly.setOrientation(LinearLayout.HORIZONTAL);
                        ly.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

                        ly.addView(labelInput);
                        ly.addView(infoInput);
                        builder.setView(ly);

                        // Set up the buttons
                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String label = labelInput.getText().toString();
                                String info = infoInput.getText().toString();

                                i.setLabel(label);
                                i.setValue(info);
                                dbInfos.updateInfo(i);

                                reloadAllData();
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
            });

        }

        //Button add new info
        Button button=(Button) view.findViewById(R.id.newInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Set the label and the information you want to create:");

                final EditText labelInput = new EditText(getActivity());
                labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                labelInput.setHint(R.string.label);

                final EditText infoInput = new EditText(getActivity());
                infoInput.setInputType(InputType.TYPE_CLASS_TEXT);
                infoInput.setHint(R.string.description);

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

                        reloadAllData();
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

        return view;
    }


    // Custom adapter pour gérer les Info
    private class CustomAdapter extends ArrayAdapter<Info>{

        private List<Info> objects;

        public CustomAdapter(Context context, int textViewResourceId, List<Info> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_info, null);
            }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
            final Info i = objects.get(position);

            if (i != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                TextView label = (TextView) v.findViewById(R.id.tv1);
                TextView  description = (TextView) v.findViewById(R.id.tv2);
                ImageButton del=(ImageButton) v.findViewById(R.id.imageButton);

                // check to see if each individual textview is null.
                // if not, assign some text!
                if (label != null){
                    label.setText(i.getLabel()+" :");
                }
                if (description != null){
                    description.setText(i.getValue());
                }

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i != null) {
                            dbInfos.deleteInfo(i);
                            reloadAllData();                        }
                    }
                });
            }

            // the view must be returned to our activity
            return v;
        }

        public List<Info> getInfos(){
            return objects;
        }
    }

    private void reloadAllData(){
        // get new modified random data
        List<Info> infos= (List<Info>) dbInfos.getAllInfosForCharacter(ch);
        // update data in our adapter
        adapter.getInfos().clear();
        if(infos!=null)
            adapter.getInfos().addAll(infos);
        // fire the event
        adapter.notifyDataSetChanged();
    }

}

