package org.uqac.android.projet.rpgsheet.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.DB.Story_MonsterDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Monster;
import org.uqac.android.projet.rpgsheet.models.Trait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by White_Knight02 on 26/04/2017.
 */

public class MonsterExpandableQuickAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Monster> monsters;

    private static LayoutInflater inflater = null;

    public MonsterExpandableQuickAdapter(Context context, List<Monster> monsters) {
        this.context = context;
        this.monsters = monsters;
        inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return monsters.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return monsters.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Monster monster = monsters.get(groupPosition);
        if (childPosition == 0)
            return monster.getHealth();
        else if (childPosition == 1)
            return monster.getStrength();
        return -1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int position, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.monster_item, null);
        TextView text = (TextView) convertView.findViewById(R.id.monsterName);
        text.setText(monsters.get(position).getName());
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.monsterDelete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Story_MonsterDB db = new Story_MonsterDB(view.getContext());
                db.deleteMonster(monsters.get(position));
                monsters.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.skill_item, null);
        TextView name = (TextView)convertView.findViewById(R.id.traitName);
        TextView value = (TextView)convertView.findViewById(R.id.saisie);

        name.setText(childPosition == 0 ? "Health" : (childPosition == 1 ? "Strength" : "MEH"));
        value.setText(String.format("%d", (int)getChild(groupPosition, childPosition)));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
