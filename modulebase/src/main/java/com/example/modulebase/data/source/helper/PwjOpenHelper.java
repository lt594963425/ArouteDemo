package com.example.modulebase.data.source.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.data.source.db.DaoMaster;


/**
 * $activityName
 *
 * @author LiuTao
 * @date 2019/4/1/001
 */


@SuppressWarnings("ALL")
public class PwjOpenHelper extends DaoMaster.OpenHelper {


    public PwjOpenHelper(Context context, String name) {
        super(context, name);
    }

    public PwjOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
