package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Bruno.J on 14/02/2017.
 */

public abstract class DBBase {

    protected final static int VERSION = 1;

    protected final static String NAME= "database.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DBBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NAME, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
