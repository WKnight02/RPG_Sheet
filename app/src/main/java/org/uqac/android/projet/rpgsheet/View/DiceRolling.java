package org.uqac.android.projet.rpgsheet.View;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.uqac.android.projet.rpgsheet.Listeners.DiceRollingListener;
import org.uqac.android.projet.rpgsheet.R;

public class DiceRolling extends ActionBarActivity {

    private final double SEUIL = 3;
    private double lastNorm = 9.8;

    private SeekBar diceSeekBar;
    private TextView diceResult;
    private EditText faceNumber;
    private Switch accToggle;

    private DiceRollingListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_rolling);
        listener = new DiceRollingListener(this, this.getWindow().getDecorView());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return listener.onTouchEvent(event);
    }

    public void Return(View view){
        /*
        Intent intent = new Intent(this, CharactersView.class);
        startActivity(intent);*/
        finish();
    }
}
