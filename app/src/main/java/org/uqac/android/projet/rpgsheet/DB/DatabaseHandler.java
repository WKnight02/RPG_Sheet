package org.uqac.android.projet.rpgsheet.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bruno.J on 10/02/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler instance;

    public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static synchronized DatabaseHandler getHandler(Context context, String name, CursorFactory factory, int version) {
        if(instance == null) {
            instance = new DatabaseHandler(context, name, factory, version);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists Character(" +
                "idCharacter integer Primary Key autoincrement, " +
                "name varchar(64) Unique" +
                ")");

        db.execSQL("Create table if not exists Story(" +
                "idStory integer Primary Key autoincrement, " +
                "title varchar(64) Unique," +
                "lore varchar(5000)" +
                ")");

        db.execSQL("Create table if not exists Item(" +
                "idItem integer Primary Key autoincrement," +
                "name varchar(64) not null" +
                ")");

                /*
                "Create table Skill(" +
                "idSkill integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "description varchar(512)" +
                ");" +

                "Create table Statistic(" +
                "idStatistic integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "baseValue integer not null," +
                "modifier integer not null" +
                ");" +

                "Create table Info(" +
                "idInfo integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "description varchar(64)" +
                ");" +
*/
        db.execSQL("Create table if not exists Character_Skill(" +
                "idCharacter integer," +
                "idSkill integer Primary Key autoincrement," +
                "label varchar(64)," +
                "description varchar(512)," +
                //"Primary Key(idCharacter, idSkill)," +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE" +
                //"Foreign Key (idSkill) REFERENCES Skill(idSkill) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Character_Statistic(" +
                "idCharacter integer," +
                "idStatistic integer Primary Key autoincrement," +
                "label varchar(64)," +
                "baseValue integer not null," +
                "modifier integer not null," +
                //"Primary Key(idCharacter, idStatistic)" +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE" +
                //"Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Character_Info(" +
                "idCharacter integer," +
                "idInfo integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "description varchar(64)," +
               // "Primary Key(idCharacter, idInfo)" +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE" +
                //"Foreign Key (idInfo) REFERENCES Info(idInfo) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Monster_Skill(" +
                "idMonster interger," +
                "idSkill integer Primary Key autoincrement," +
                "label varchar(64)," +
                "description varchar(512)," +
                //"Primary Key(idMonster, idSkill)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE" +
                //"Foreign Key (idSkill) REFERENCES Skill(idSkill) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Monster_Statistic(" +
                "idMonster integer," +
                "idStatistic integer Primary Key autoincrement," +
                "label varchar(64)," +
                "baseValue integer not null," +
                "modifier integer not null," +
                //"Primary Key(idMonster, idStatistic)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE" +
                //"Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Monster_Info(" +
                "idMonster integer," +
                "idInfo integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "description varchar(64)," +
                //"Primary Key(idMonster, idInfo)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE" +
                //"Foreign Key (idInfo) REFERENCES Info(idInfo) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Item_Statistic(" +
                "idItem integer," +
                "idStatistic integer Primary Key autoincrement," +
                "label varchar(64)," +
                "baseValue integer not null," +
                "modifier integer not null," +
                //"Primary Key(idItem, idStatistic)" +
                "Foreign Key (idItem) REFERENCES Item(idItem) ON DELETE CASCADE" +
                //"Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Story_Monster(" +
                "idStory integer," +
                "idMonster integer Primary Key autoincrement," +
                "name varchar(64) not NULL," +
                "Foreign Key (idStory) REFERENCES Story(idStory) ON DELETE CASCADE" +
                ")");

        db.execSQL("Create table if not exists Character_Item(" +
                "idCharacter integer," +
                "idItem integer," +
                "Primary Key(idCharacter, idItem)," +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE," +
                "Foreign Key (idItem) REFERENCES Item(idItem) ON DELETE CASCADE" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if(oldV != newV) {
            String drops = "" +
                    "Drop table if exists Character_Item;\n" + // Must be first else drop fails
                    "Drop table if exists Character;\n" +
                    "Drop table if exists Item;\n" +
                    "Drop table if exists Monster;\n" +
                    "Drop table if exists Story;\n" +
                    "Drop table if exists Character_Skill;\n" +
                    "Drop table if exists Story_Monster;\n" +
                    "Drop table if exists Character_Statistic;\n" +
                    "Drop table if exists Character_Info;\n" +
                    "Drop table if exists Monster_Skill;\n" +
                    "Drop table if exists Monster_Statistic;\n" +
                    "Drop table if exists Monster_Info;\n" +
                    "Drop table if exists Item_Statistic;\n" +
                    "";

            for(String drop : drops.split("\n")) {
                Log.w("drop", drop);
                db.execSQL(drop);
            }

            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
