package org.uqac.android.projet.rpgsheet.DB;

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
                "idCharacter integer Primary Key autoincrement, " +
                "name varchar(64)" +
                ");" +

                "Create table Story (" +
                "idStory integer Primary Key autoincrement, " +
                "title varchar(64)," +
                "lore varchar(5000)" +
                ");" +

                "Create table Monster(" +
                "idMonster integer Primary Key autoincrement," +
                "name varchar(64) not NULL" +
                ");" +

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

                "Create table Item(" +
                "idItem integer Primary Key autoincrement," +
                "name varchar(64) not null" +
                ");" +

                "Create table Info(" +
                "idInfo integer Primary Key autoincrement, " +
                "label varchar(64)," +
                "description varchar(64)" +
                ");" +

                "Create table Character_Skill(" +
                "idCharacter integer," +
                "idSkill integer," +
                "Primary Key(idCharacter, idSkill)," +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE," +
                "Foreign Key (idSkill) REFERENCES Skill(idSkill) ON DELETE CASCADE" +
                ");" +

                "Create table Story_Monster(" +
                "idStory integer," +
                "idMonster integer," +
                "Primary Key(idStory, idMonster)," +
                "Foreign Key (idStory) REFERENCES Story(idStory) ON DELETE CASCADE," +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE" +
                ");" +

                "Create table Character_Statistic(" +
                "idCharacter integer," +
                "idStatistic integer," +
                "Primary Key(idCharacter, idStatistic)" +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE," +
                "Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ");" +

                "Create table Character_Info(" +
                "idCharacter integer," +
                "idInfo integer," +
                "Primary Key(idCharacter, idInfo)" +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE," +
                "Foreign Key (idInfo) REFERENCES Info(idInfo) ON DELETE CASCADE" +
                ");" +

                "Create table Monster_Skill(" +
                "idMonster interger," +
                "idSkill integer," +
                "Primary Key(idMonster, idSkill)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE," +
                "Foreign Key (idSkill) REFERENCES Skill(idSkill) ON DELETE CASCADE" +
                ");" +

                "Create table Monster_Statistic(" +
                "idMonster integer," +
                "idStatistic integer," +
                "Primary Key(idMonster, idStatistic)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE," +
                "Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ");"+

                "Create table Monster_Info(" +
                "idMonster integer," +
                "idInfo integer," +
                "Primary Key(idMonster, idInfo)" +
                "Foreign Key (idMonster) REFERENCES Monster(idMonster) ON DELETE CASCADE," +
                "Foreign Key (idInfo) REFERENCES Info(idInfo) ON DELETE CASCADE" +
                ");" +

                "Create table Item_Statistic(" +
                "idItem integer," +
                "idStatistic integer," +
                "Primary Key(idItem, idStatistic)" +
                "Foreign Key (idItem) REFERENCES Item(idItem) ON DELETE CASCADE," +
                "Foreign Key (idStatistic) REFERENCES Statistic(idStatistic) ON DELETE CASCADE" +
                ");" +

                "Create table Character_Item(" +
                "idCharacter integer," +
                "idItem integer," +
                "Primary Key(idCharacter, idItem)" +
                "Foreign Key (idCharacter) REFERENCES Character(idCharacter) ON DELETE CASCADE," +
                "Foreign Key (idItem) REFERENCES Item(idItem) ON DELETE CASCADE" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("Drop table if exists Character;" +
                "Drop table if exists Item;" +
                "Drop table if exists Monster;" +
                "Drop table if exists Skill;" +
                "Drop table if exists Info;" +
                "Drop table if exists Story;" +
                "Drop table if exists Statistic;" +
                "Drop table if exists Character_Skill;" +
                "Drop table if exists Story_Monster;" +
                "Drop table if exists Character_Statistic;" +
                "Drop table if exists Character_Info;" +
                "Drop table if exists Monster_Skill;" +
                "Drop table if exists Monster_Statistic;" +
                "Drop table if exists Monster_Info;" +
                "Drop table if exists Item_Statistic;" +
                "Drop table if exists Character_Item;" +
                "");
        onCreate(db);
    }
}
