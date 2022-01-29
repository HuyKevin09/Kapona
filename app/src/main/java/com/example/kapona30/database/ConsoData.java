package com.example.kapona30.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Base de données.
 * <br>Définitions de la table, clé primaire, colonnes...
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.DialogConso}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoSemaineFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoMoisFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoProdFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.favoris.FavorisFragment}</li>
 *     <li>{@link com.example.kapona30.favoris.AdapterFavoris}</li>
 *     <li>{@link com.example.kapona30.historique.HistoriqueFragment}</li>
 *     <li>{@link com.example.kapona30.historique.AdapterHistorique}</li>
 *     <li>{@link com.example.kapona30.liste.ListeFragment}</li>
 *     <li>{@link com.example.kapona30.liste.AdapterListe}</li>
 * </ul>
 */

@Entity(tableName = "table")
public class ConsoData implements Serializable {
    /**
     * ID généré automatiquement.
     */
    @PrimaryKey(autoGenerate = true)
    private int ID;

    /**
     * Quantité consommée. Cette quantité est mise à 0.0 lorsqu'il s'agit d'un produit de
     * la liste, des favoris ou de l'historique.
     */
    @ColumnInfo(name = "quantite")
    private double quantite;

    /**
     * Code-barres du produit.
     */
    @ColumnInfo(name = "code_barres")
    private String codeBarres;

    /**
     * Numéro du jour durant lequel le produit à été inséré dans la base de données.
     */
    @ColumnInfo(name = "numero_jour")
    private int numeroJour;

    /**
     * Numéro de la semaine durant laquelle le produit à été inséré dans la base de données.
     */
    @ColumnInfo(name = "numero_semaine")
    private int numeroSemaine;

    /**
     * Numéro du mois durant lequel le produit à été inséré dans la base de données.
     */
    @ColumnInfo(name = "numero_mois")
    private int numeroMois;

    /**
     * Nature du produit.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     */
    @ColumnInfo(name = "nature")
    private String nature;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getCodeBarres() {
        return codeBarres;
    }

    public void setCodeBarres(String codeBarres) {
        this.codeBarres = codeBarres;
    }

    public int getNumeroJour() {
        return numeroJour;
    }

    public void setNumeroJour(int numeroJour) {
        this.numeroJour = numeroJour;
    }

    public int getNumeroSemaine() {
        return numeroSemaine;
    }

    public void setNumeroSemaine(int numeroSemaine) {
        this.numeroSemaine = numeroSemaine;
    }

    public int getNumeroMois() {
        return numeroMois;
    }

    public void setNumeroMois(int numeroMois) {
        this.numeroMois = numeroMois;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}