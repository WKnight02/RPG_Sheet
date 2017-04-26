package org.uqac.android.projet.rpgsheet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.DB.Story_MonsterDB;
import org.uqac.android.projet.rpgsheet.R;
import org.uqac.android.projet.rpgsheet.models.Monster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by White_Knight02 on 26/04/2017.
 */

public class MonsterQuickAdapter extends BaseAdapter {
    Context context;

    List<Monster> monsters;

    private static LayoutInflater inflater = null;

    public MonsterQuickAdapter(Context context, ArrayList<Monster> monsters) {
        this.context = context;
        this.monsters = monsters;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return monsters.size();
    }

    @Override
    public Object getItem(int position) {
        return monsters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.monster_item, null);
        TextView text = (TextView) vi.findViewById(R.id.monsterName);
        text.setText(monsters.get(position).getName());
        ImageButton delete = (ImageButton)vi.findViewById(R.id.monsterDelete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Story_MonsterDB db = new Story_MonsterDB(view.getContext());
                db.deleteMonster(monsters.get(position));
                monsters.remove(position);
                notifyDataSetChanged();
            }
        });

        return vi;
    }
}
