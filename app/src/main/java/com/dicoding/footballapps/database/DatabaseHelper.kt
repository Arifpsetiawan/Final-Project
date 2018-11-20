package com.dicoding.footballapps.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "APuSe.db",null,1){

    companion object {
        private  var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance =
                        DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MatchFavDB.TABLE_FAVORITE_MATCH,true,
            MatchFavDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MatchFavDB.ID_EVENT to TEXT + UNIQUE,
            MatchFavDB.DATE_EVENT to TEXT,
            MatchFavDB.HOME_TEAM to TEXT,
            MatchFavDB.AWAY_TEAM to TEXT,
            MatchFavDB.HOME_SCORE to TEXT,
            MatchFavDB.AWAY_SCORE to TEXT,
            MatchFavDB.HOME_GOAL_DETAIL to TEXT,
            MatchFavDB.AWAY_GOAL_DETAIL to TEXT,
            MatchFavDB.TIME_EVENT to TEXT
        )

        db.createTable(TeamFavDB.TABLE_FAVORITE_TEAM, true,
            TeamFavDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamFavDB.ID_TEAM to TEXT + UNIQUE,
            TeamFavDB.NAME_TEAM to TEXT,
            TeamFavDB.IMAGE_TEAM to TEXT,
            TeamFavDB.YEAR_TEAM to TEXT,
            TeamFavDB.STADIUM_TEAM to TEXT,
            TeamFavDB.STADIUM_THUMB to TEXT,
            TeamFavDB.FANART1_TEAM to TEXT,
            TeamFavDB.FANART2_TEAM to TEXT,
            TeamFavDB.DESC_TEAM to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchFavDB.TABLE_FAVORITE_MATCH,true)
        db.dropTable(TeamFavDB.TABLE_FAVORITE_TEAM, true)
    }

}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)