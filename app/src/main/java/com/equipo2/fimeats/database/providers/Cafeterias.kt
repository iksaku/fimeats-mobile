package com.equipo2.fimeats.database.providers

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.equipo2.fimeats.models.Cafeteria
import com.equipo2.fimeats.models.Faculty

object Cafeterias {

    fun find(db: SQLiteDatabase, faculty: Faculty): Cafeteria? {
        val cursor: Cursor = db.rawQuery("SELECT * FROM cafeterias WHERE faculty_id = ${faculty.id} LIMIT 1", null).also {
            it.moveToFirst()
        }

        var cafeteria: Cafeteria? = null;
        while (!cursor.isAfterLast) {
            cafeteria = Cafeteria(
                cursor.getString(0),
                faculty,
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
            )
            cursor.moveToNext()
        }
        cursor.close()
        return cafeteria;
    }
}