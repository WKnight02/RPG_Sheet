package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class StatisticDB extends DBBase{
    public static final String TABLE_NAME = "Statistic";
    public static final String ID="idStatistic";
    public static final String VALUE= "baseValue";
    public static final String MAXVALUE="modifier";
    public static final String LABEL= "description";

    public StatisticDB(Context pContext) {
        super(pContext);
    }
}
