package com.example.lebonvoisin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BaseDeDonnee(context: Context) :
    SQLiteOpenHelper(context, "base_de_donnee.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE donnee (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                personne TEXT NOT NULL,
                lieu TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Données de test
        db.execSQL("INSERT INTO donnee (personne, lieu) VALUES ('Alice', 'Paris')")
        db.execSQL("INSERT INTO donnee (personne, lieu) VALUES ('Bob', 'Lyon')")
        db.execSQL("INSERT INTO donnee (personne, lieu) VALUES ('Charlie', 'Marseille')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS donnee")
        onCreate(db)
    }

    fun recupere_de_la_base(): List<String> {
        val liste = mutableListOf<String>()
        var curseur: android.database.Cursor? = null
        var curseur2: android.database.Cursor? = null

        try {
            val db = readableDatabase

            curseur = db.rawQuery("SELECT personne, lieu FROM donnee", null)

            if (curseur.moveToFirst()) {
                do {
                    val personne = curseur.getString(0)
                    val lieu = curseur.getString(1)
                    liste.add("$personne - $lieu")
                } while (curseur.moveToNext())
            }

            curseur2 = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table'",
                null
            )
            while (curseur2.moveToNext()) {
                Log.d("DB_TABLE", curseur2.getString(0))
            }

            curseur.close()
            curseur2.close()
            db.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return liste
    }
}