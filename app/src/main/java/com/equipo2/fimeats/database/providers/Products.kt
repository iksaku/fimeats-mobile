package com.equipo2.fimeats.database.providers

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.equipo2.fimeats.models.Cafeteria
import com.equipo2.fimeats.models.Product

object Products {
    fun get(db: SQLiteDatabase, cafeteria: Cafeteria): ArrayList<Product> {
        val cursor: Cursor = db.rawQuery("SELECT * FROM products WHERE cafeteria_id = ${cafeteria.id}", null).also {
            it.moveToFirst()
        }

        val products: ArrayList<Product> = ArrayList()
        while (!cursor.isAfterLast) {
            products.add(
                Product(
                    cursor.getString(0),
                    cafeteria,
                    cursor.getString(2),
                    cursor.getString(3) ?: "",
                    cursor.getString(4).toDouble(),
                    cursor.getString(5) ?: "",
                    cursor.getString(6),
                    cursor.getString(7),
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        return products;
    }
}