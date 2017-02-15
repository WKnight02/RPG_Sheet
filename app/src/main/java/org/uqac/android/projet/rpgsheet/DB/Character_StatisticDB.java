package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_StatisticDB extends DBBase {

    public static final String TABLE_NAME = "Character_Statistic";
    public static final String IDCharacter="idCharacter";
    public static final String IDTrait= "idStatistic";

    public Character_StatisticDB(Context pContext) {
        super(pContext);
    }
}
