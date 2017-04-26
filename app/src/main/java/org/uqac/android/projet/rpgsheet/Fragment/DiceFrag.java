package org.uqac.android.projet.rpgsheet.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.Listeners.DiceRollingListener;
import org.uqac.android.projet.rpgsheet.R;

/**
 * Created by Bruno.J on 16/03/2017.
 */

public class DiceFrag extends Fragment {

    private DiceRollingListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dice_rolling, container, false);

        if(getActivity()==null) {
            return view;
        }

        listener = new DiceRollingListener(getActivity(), view);

        view.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                return listener.onTouchEvent(event);
            }
        });

        return view;
    }
}
