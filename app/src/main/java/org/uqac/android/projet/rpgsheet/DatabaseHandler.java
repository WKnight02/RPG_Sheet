package org.uqac.android.projet.rpgsheet;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Created by Bruno.J on 10/02/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Character(" +
                "name varchar(64) Primary Key" +
                ");" +

                "Create table Story (" +
                "title varchar(64) Primary Key," +
                "lore varchar(5000)" +
                ");" +

                "Create table Monster(" +
                "idMonster integer Primary Key autoincrement," +
                "name varchar(64) not NULL" +
                ");" +

                "Create table Skill(" +
                "label varchar(64) Primary Key," +
                "description varchar(512)" +
                ");" +

                "Create table Statistic(" +
                "label varchar(64) Primary Key," +
                "baseValue integer not null," +
                "modifier integer not null" +
                ");" +

                "Create table Item(" +
                "id integer Primary Key autoincrement," +
                "name varchar(64) not null" +
                ");" +

                "Create table Info(" +
                "label varchar(64) Primary Key" +
                "description varchar(64)" +
                ");" +

                "Create table Character_Skill(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)," +
                "Foreign Key (name) REFERENCES Character(name)," +
                "Foreign Key (label) REFERENCES Skill(label)" +
                ");" +

                "Create table Character_Statistic(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Character(name)," +
                "Foreign Key (label) REFERENCES Statistic(label)" +
                ");" +

                "Create table Character_Info(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Character(name)," +
                "Foreign Key (label) REFERENCES Info(label)" +
                ");" +

                "Create table Monster_Skill(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Monster(name)," +
                "Foreign Key (label) REFERENCES Skill(label)" +
                ");" +

                "Create table Monster_Statistic(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Monster(name)," +
                "Foreign Key (label) REFERENCES Statistic(label)" +
                ");"+

                "Create table Monster_Info(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Monster(name)," +
                "Foreign Key (label) REFERENCES Info(label)" +
                ");" +

                "Create table Item_Statistic(" +
                "name varchar(64)," +
                "label varchar(64)" +
                "Primary Key(name, label)" +
                "Foreign Key (name) REFERENCES Item(name)," +
                "Foreign Key (label) REFERENCES Statistic(label)" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
