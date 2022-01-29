package com.example.kapona30.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Permet l'initialisation de la base de données.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.DialogConso}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoSemaineFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoMoisFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoProdFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.favoris.FavorisFragment}</li>
 *     <li>{@link com.example.kapona30.historique.HistoriqueFragment}</li>
 *     <li>{@link com.example.kapona30.liste.ListeFragment}</li>
 * </ul>
 */

@Database(entities = {ConsoData.class}, version = 1, exportSchema = false)
public abstract class
RoomDB extends RoomDatabase {

    /**
     * Nom de la base de données
     */
    private static final String DATABASE_NAME = "database";

    /**
     * Base de données
     */
    private static RoomDB database;

    /**
     * Ouverture d'une instance de la base de données. Si elle n'existe pas encore, la base de
     * données est créée.
     * @param context
     * @return La base de données
     */
    public synchronized static RoomDB getInstance(Context context){
        if(database == null){
            //Initialisation si la base de données n'existe pas encore
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    /**
     * Permet de faire des requêtes à la base de données
     */
    public abstract ConsoDao consoDao();
}
