package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Bruno.J on 14/02/2017.
 */

public class DBBase {

    protected final static int VERSION = 4;

    protected final static String NAME= "database.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;
    protected Context context;

    public DBBase(Context pContext) {
        this.context=pContext;
        this.mHandler = new DatabaseHandler(pContext, NAME, null, VERSION);
    }

    public void open() {
        if(mHandler==null)
            mHandler=DatabaseHandler.getHandler(context, NAME, null, VERSION);

        mDb = mHandler.getWritableDatabase();
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }


    public Cursor showAllTables(){
        open();
        String mySql = " SELECT name FROM sqlite_master " + " WHERE type='table'";
        Cursor curs= mDb.rawQuery(mySql, null);
        return curs;
    }
}
