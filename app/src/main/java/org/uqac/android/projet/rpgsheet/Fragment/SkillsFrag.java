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
import org.uqac.android.projet.rpgsheet.DB.Character_SkillDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Skill;

import java.util.List;

/**
 * Created by Bruno.J on 16/03/2017.
 */

public class SkillsFrag extends Fragment {
    private Character ch;
    private CharacterDB dbCharacter;
    private Character_SkillDB dbSkill;
    private String name;
    private Context activity;
    private CustomAdapter adapter;
    private ListView listSkills;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skills_view, container, false);
        listSkills = (ListView) view.findViewById(R.id.infos);

        if (getArguments() == null ||(activity = getActivity()) == null) {
            return view;
        }

        name = (String) getArguments().getString("name");

        Log.d("name", name);

        dbCharacter = new CharacterDB(activity);
        dbSkill = new Character_SkillDB(activity);

        ch = dbCharacter.getCharacterByName(name);
        List<Skill> skills = (List<Skill>) dbSkill.getAllSkillsForCharacter(ch);

        if (skills != null) {
            Log.d("skills", skills.toString());
            adapter = new CustomAdapter(activity, android.R.layout.simple_list_item_1, skills);
            listSkills.setAdapter(adapter);


            //Modifier valeurs
            listSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    List<Skill> skills;
                    if((skills=adapter.getSkills())!=null) {
                        final Skill i = skills.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Edit the label and the description:");

                        final EditText labelInput = new EditText(getActivity());
                        labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        labelInput.setHint(R.string.label);
                        labelInput.setText(i.getLabel()+"");

                        final EditText infoInput = new EditText(getActivity());
                        infoInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        infoInput.setHint(R.string.description);
                        infoInput.setText(i.getDescription());

                        final LinearLayout ly = new LinearLayout(getActivity());
                        ly.setOrientation(LinearLayout.VERTICAL);
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
                                i.setDescription(info);
                                dbSkill.updateSkill(i);

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
                builder.setTitle("Set the label and the description you want to create:");

                final EditText labelInput = new EditText(getActivity());
                labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                labelInput.setHint(R.string.label);

                final EditText infoInput = new EditText(getActivity());
                infoInput.setInputType(InputType.TYPE_CLASS_TEXT);
                infoInput.setHint(R.string.description);

                final LinearLayout ly=new LinearLayout(getActivity());
                ly.setOrientation(LinearLayout.VERTICAL);
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

                        Skill newSkill=new Skill(label, info);
                        dbSkill.insertSkill(newSkill, ch);

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
    private class CustomAdapter extends ArrayAdapter<Skill> {

        private List<Skill> objects;

        public CustomAdapter(Context context, int textViewResourceId, List<Skill> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_skill, null);
            }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
            final Skill i = objects.get(position);

            if (i != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                TextView label = (TextView) v.findViewById(R.id.tv1);
                TextView  description = (TextView) v.findViewById(R.id.tv2);
                ImageButton del=(ImageButton) v.findViewById(R.id.imageButton);

                // check to see if each individual textview is null.
                // if not, assign some text!
                if (label != null){
                    label.setText(i.getLabel()+"");
                }
                if (description != null){
                    description.setText(i.getDescription());
                }

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i != null) {
                            dbSkill.deleteSkill(i);
                            reloadAllData();                        }
                    }
                });
            }

            // the view must be returned to our activity
            return v;
        }

        public List<Skill> getSkills(){
            return objects;
        }
    }

    private void reloadAllData(){
        // get new modified random data
        List<Skill> skills= (List<Skill>) dbSkill.getAllSkillsForCharacter(ch);
        // update data in our adapter
        if(adapter==null && skills!=null){
            adapter= new CustomAdapter(activity, android.R.layout.simple_list_item_1, skills);

        }
        else{
        if(adapter.getSkills()!=null)
            adapter.getSkills().clear();
        if(skills!=null)
            adapter.getSkills().addAll(skills);
        // fire the event
        }
        adapter.notifyDataSetChanged();
    }

}
