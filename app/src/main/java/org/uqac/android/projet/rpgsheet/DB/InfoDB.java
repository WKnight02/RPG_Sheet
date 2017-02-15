package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;
import android.database.Cursor;

import org.uqac.android.projet.rpgsheet.models.Info;

/**
 * Created by Bruno.J on 14/02/2017.
 */

public class InfoDB extends DBBase {
    public static final String TABLE_NAME = "Info";
    public static final String ID="idInfo";
    public static final String VALUE= "value";
    public static final String LABEL= "description";

    public InfoDB(Context pContext) {
        super(pContext);
    }


    public long getId(Info info) {
        Cursor curs= mDb.query(TABLE_NAME, new String[]{ID}, LABEL+"=?", new String[]{info.getLabel()},null,null,null);
        curs.moveToFirst();
        return curs.getInt(0);
    }
}
