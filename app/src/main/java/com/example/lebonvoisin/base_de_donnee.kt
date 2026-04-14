package com.example.lebonvoisin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDeDonnee(context: Context) :
    SQLiteOpenHelper(context, "base_de_donnee.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun recupere_de_la_base(): List<String> {
        val liste = mutableListOf<String>()

        try {
            val db = readableDatabase
            val curseur = db.rawQuery("SELECT personne, lieu FROM donnee", null)

            if (curseur.moveToFirst()) {
                do {
                    val personne = curseur.getString(0)
                    val lieu = curseur.getString(1)
                    liste.add("$personne - $lieu")
                } while (curseur.moveToNext())
            }

            curseur.close()
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return liste
    }
}