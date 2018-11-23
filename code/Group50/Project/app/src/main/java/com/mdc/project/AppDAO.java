package com.mdc.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class AppDAO extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final String TABLE_NAME = "characters";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase defaultWritableDB;

    public AppDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        defaultWritableDB = db;
        String createTableSql = "CREATE TABLE " + TABLE_NAME
                + "(id PRIMARY KEY, icon INTEGER, award TEXT, name TEXT, type TEXT, existValue INTEGER, " +
                "attackValue INTEGER, skillValue INTEGER, difficulty INTEGER, passive TEXT, skill1 TEXT, skill2 TEXT, skill3 TEXT)";
        db.execSQL(createTableSql);
        //初始化
        insert(new Character(R.drawable.jialuo, "花见巫女", "伽罗", "射手",
                2, 9, 4, 2, "破魔之箭", "渡灵之箭", "静默之箭", "纯净之域"));
        insert(new Character(R.drawable.yuange, "无间傀儡", "元歌", "刺客",
                1, 5, 9, 10, "秘术～操控", "秘术～影", "秘术～纸雏鸾", "秘术～十字闪"));
        insert(new Character(R.drawable.simayi, "寂灭之心", "司马懿", "刺客",
                5, 5, 9, 5, "静默之语", "幽影之咬", "荒芜之域", "死神降临"));
        insert(new Character(R.drawable.sunce, "光明之海", "孙策", "坦克",
                7, 6, 7, 6, "怒潮", "劈风斩浪", "惊涛骇浪", "长风破浪"));
        insert(new Character(R.drawable.yixing, "天元之弈", "弈星", "法师",
                4, 3, 9, 6, "气合", "定式～镇神", "定式～倚盖", "天元"));
        insert(new Character(R.drawable.yangyuhuan, "霓裳风华", "杨玉环", "法师",
                3, 3, 9, 5, "惊鸿调", "霓裳曲", "胡旋乐", "长恨歌"));
        insert(new Character(R.drawable.shenmengxi, "爆弹怪猫", "沈梦溪", "法师",
                3, 1, 8, 4, "暴躁节奏", "喵咪炸弹", "正常操作", "综合爆款"));
        insert(new Character(R.drawable.kuangtie, "战车意志", "狂铁", "战士",
                6, 6, 6, 5, "无畏战车", "碎裂之刃", "强袭风暴", "力场压制"));
        insert(new Character(R.drawable.dunshan, "极冰防御线", "盾山", "辅助",
                10, 3, 4, 3, "天地化盾", "一夫当关", "万夫莫开", "不动如山"));
        insert(new Character(R.drawable.peiqinhu, "六合虎拳", "裴擒虎", "刺客",
                4, 8, 6, 7, "寸劲", "冲拳式", "气守式", "虎啸风生"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //暂时不需实现
    }

    //使用SQLiteStatement实现数据库的insert,update,delete。
    //return the row ID of the last row inserted, if this insert is successful. -1 otherwise.
    public long insert(Character character) {
        SQLiteDatabase db = getWritableDatabase();
        String insertSql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(insertSql);
        int index = 1;
        statement.bindLong(index++, character.getId());
        statement.bindLong(index++, character.getIcon());
        statement.bindString(index++, character.getAward());
        statement.bindString(index++, character.getName());
        statement.bindString(index++, character.getType());
        statement.bindLong(index++, character.getExistValue());
        statement.bindLong(index++, character.getAttackValue());
        statement.bindLong(index++, character.getSkillValue());
        statement.bindLong(index++, character.getDifficulty());
        statement.bindString(index++, character.getPassive());
        statement.bindString(index++, character.getSkill1());
        statement.bindString(index++, character.getSkill2());
        statement.bindString(index++, character.getSkill3());
        long rowID = statement.executeInsert();
        return rowID;
    }

    //return the number of rows affected by this SQL statement execution.
    public int update(Character updateCharacter, Character oldCharacter) {
        SQLiteDatabase db = getWritableDatabase();
        String updateSql = "UPDATE " + TABLE_NAME + " SET id = ?, icon = ?, award = ?, name = ?, type = ?, existValue = ?, " +
                "attackValue = ?, skillValue = ?, difficulty = ?, passive = ?, skill1 = ?, skill2 = ?, skill3 = ? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(updateSql);
        int index = 1;
        statement.bindLong(index++, updateCharacter.getId());
        statement.bindLong(index++, updateCharacter.getIcon());
        statement.bindString(index++, updateCharacter.getAward());
        statement.bindString(index++, updateCharacter.getName());
        statement.bindString(index++, updateCharacter.getType());
        statement.bindLong(index++, updateCharacter.getExistValue());
        statement.bindLong(index++, updateCharacter.getAttackValue());
        statement.bindLong(index++, updateCharacter.getSkillValue());
        statement.bindLong(index++, updateCharacter.getDifficulty());
        statement.bindString(index++, updateCharacter.getPassive());
        statement.bindString(index++, updateCharacter.getSkill1());
        statement.bindString(index++, updateCharacter.getSkill2());
        statement.bindString(index++, updateCharacter.getSkill3());
        statement.bindLong(index++, oldCharacter.getId());
        int affectedRows = statement.executeUpdateDelete();
        return affectedRows;
    }

    //return the number of rows affected by this SQL statement execution.
    public int delele(Character character) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(deleteSql);
        statement.bindLong(1, character.getId());
        int affectedRows = statement.executeUpdateDelete();
        return affectedRows;
    }

    //通过name查询
    public Cursor query(String name) {
        //暂时不需要实现
        return null;
    }

    //查询所有characters表的数据项
    public Cursor queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //未修改的getWritableDatabase()中会调用onCreate()函数，使初始化会抛出递归调用异常
    @Override
    public SQLiteDatabase getWritableDatabase() {
        if(defaultWritableDB != null) {
            return defaultWritableDB;
        }
        return super.getWritableDatabase();
    }
}
