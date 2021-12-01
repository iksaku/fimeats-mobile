package com.equipo2.fimeats.database.providers

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.equipo2.fimeats.models.Cafeteria
import com.equipo2.fimeats.models.Faculty

object Faculties {
    fun get(db: SQLiteDatabase): ArrayList<Faculty> {
        val cursor: Cursor = db.rawQuery("SELECT * FROM faculties", null).also {
            it.moveToFirst()
        }

        val faculties: ArrayList<Faculty> = ArrayList()
        while (!cursor.isAfterLast) {
            faculties.add(
                Faculty(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        return faculties;
    }

}