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
import org.uqac.android.projet.rpgsheet.DB.Character_StatisticDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Character;
import org.uqac.android.projet.rpgsheet.models.Trait;

import java.util.List;

/**
 * Created by Bruno.J on 16/03/2017.
 */
public class TraitsFrag extends Fragment{
    private Character ch;
    private CharacterDB dbCharacter;
    private Character_StatisticDB dbStatistics;
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
        View view = inflater.inflate(R.layout.traits_view, container, false);
        listInfos = (ListView) view.findViewById(R.id.infos);

        if (getArguments() == null ||(activity = getActivity()) == null) {
            return view;
        }

        name = (String) getArguments().getString("name");

        Log.d("name", name);

        dbCharacter = new CharacterDB(activity);
        dbStatistics = new Character_StatisticDB(activity);

        ch = dbCharacter.getCharacterByName(name);
        List<Trait> infos = (List<Trait>) dbStatistics.getAllStatisticsForCharacter(ch);

        if (infos != null) {
            adapter = new CustomAdapter(activity, android.R.layout.simple_list_item_1, infos);
            listInfos.setAdapter(adapter);


            //Modifier valeurs
            listInfos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    List<Trait> infos;
                    Log.d("click?","click");
                    if((infos=adapter.getTraits())!=null) {
                        final Trait i = infos.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Edit the label or the value");

                        final EditText labelInput = new EditText(getActivity());
                        labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        labelInput.setHint(R.string.label);
                        labelInput.setText(i.getLabel()+""/*.substring(0, i.getLabel().length() - 3)*/);

                        final EditText infoInput = new EditText(getActivity());
                        infoInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        infoInput.setHint(R.string.value);
                        infoInput.setText(i.getValue()+"");

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
                                Integer value= Integer.parseInt(infoInput.getText().toString());
                                i.setLabel(label);
                                i.setValue(value);
                                dbStatistics.updateStatistic(i);

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
        Button button=(Button) view.findViewById(R.id.newTrait);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Set the label and the based value of the statistic you want to create:");

                final EditText labelInput = new EditText(getActivity());
                labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                labelInput.setHint(R.string.label);

                final EditText infoInput = new EditText(getActivity());
                infoInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                infoInput.setHint(R.string.value);

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
                        Integer info= Integer.parseInt(infoInput.getText().toString());

                        Log.d("valeur de base", info+"");

                        Trait newInfo=new Trait(label, info);
                        dbStatistics.insertStatistic(newInfo, ch);

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
    private class CustomAdapter extends ArrayAdapter<Trait>{

        private List<Trait> objects;

        public CustomAdapter(Context context, int textViewResourceId, List<Trait> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.trait_item, null);
            }

            final Trait i = objects.get(position);

            if (i != null) {

                TextView label = (TextView) v.findViewById(R.id.traitName);
                TextView description = (TextView) v.findViewById(R.id.traitModified);
                ImageButton del = (ImageButton) v.findViewById(R.id.imageButton);
                ImageButton moins = (ImageButton) v.findViewById(R.id.moins);
                ImageButton plus = (ImageButton) v.findViewById(R.id.plus);
                final EditText modifier = (EditText) v.findViewById(R.id.saisie);
                modifier.setSelectAllOnFocus(true);
                // check to see if each individual textview is null.
                // if not, assign some text!
                if (label != null) {
                    label.setText(i.getLabel() + " :");
                }
                if (description != null) {
                    //TODO RAJOUTER LES ITEMS
                    Log.d("affichage val combinées",i.getModifier()+" "+i.getValue());
                    int value=i.getModifier()+i.getValue();
                    if(value<0){
                        value=0;
                    }
                    description.setText(value+"");
                }

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (i != null) {
                            Log.d("id del", i.getId()+"");
                            dbStatistics.deleteStatistic(i);
                            reloadAllData();
                        }
                    }
                });

                moins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (i != null) {
                            i.setModifier(i.getModifier() - 1);
                            Log.d("id moins", i.getId()+"");
                            dbStatistics.updateStatistic(i);
                            reloadAllData();

                        }
                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (i != null) {
                            i.setModifier(i.getModifier() + 1);
                            dbStatistics.updateStatistic(i);
                            reloadAllData();

                        }
                    }
                });

                modifier.setText(i.getModifier()+"");

                modifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Set the modifier value:");

                        final EditText labelInput = new EditText(getActivity());
                        labelInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        labelInput.setHint("value");
                        labelInput.setText(i.getModifier()+"");

                        builder.setView(labelInput);

                        // Set up the buttons
                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer label = Integer.parseInt(labelInput.getText().toString());

                                i.setModifier(label);
                                dbStatistics.updateStatistic(i);

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
                /*
                modifier.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                            if(i!=null && modifier.getText()!=null){
                                i.setModifier(Integer.parseInt(modifier.getText().toString()));
                                dbStatistics.updateStatistic(i);
                                reloadAllData();
                            }
                    }
                });*/

                /*
                modifier.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if(i!=null && modifier.getText()!=null){
                                i.setModifier(Integer.parseInt(modifier.getText().toString()));
                                dbStatistics.updateStatistic(i);
                                reloadAllData();
                            }
                        }
                    }
                });*/
            }
                // the view must be returned to our activity
                return v;
        }

        public List<Trait> getTraits(){
            return objects;
        }
    }

    private void reloadAllData(){
        // get new modified random data
        List<Trait> infos= (List<Trait>) dbStatistics.getAllStatisticsForCharacter(ch);
        // update data in our adapter

        if(adapter==null && infos!=null){
            adapter= new CustomAdapter(activity, android.R.layout.simple_list_item_1, infos);

        }
        if (adapter.getTraits() != null)
                adapter.getTraits().clear();
        if (infos != null)
                adapter.getTraits().addAll(infos);
            // fire the event
        adapter.notifyDataSetChanged();
    }

}

