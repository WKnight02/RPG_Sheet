package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class Character_InfoDB extends DBBase {
    public static final String TABLE_NAME = "Info";
    public static final String IDCharacter="idCharacter";
    public static final String IDInfo= "idInfo";

    public Character_InfoDB(Context pContext) {
        super(pContext);
    }
}
