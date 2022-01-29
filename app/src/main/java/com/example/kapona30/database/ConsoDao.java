package com.example.kapona30.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Requêtes possibles vers la base de données.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
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

@Dao
public interface ConsoDao {

    /**
     * Insertion d'un produit dans la base de données
     * @param consoData Produit à insérer
     */
    @Insert(onConflict = REPLACE)
    void insert(ConsoData consoData);

    /**
     * Suppression d'un produit de la base de données
     * @param consoData Produit à supprimer
     */
    @Delete
    void delete(ConsoData consoData);

    /**
     * Suppression de la base de données de tous les produits contenus dans la liste
     * @param consoDataList Liste des produits à supprimer
     */
    @Delete
    void reset(List<ConsoData> consoDataList);

    /**
     * Récupération des produits d'une certaine nature.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNature Nature recherchée
     * @return Liste des produits de la base de données ayant la même nature que celle passée en
     * paramètre
     */
    @Query("SELECT * FROM `table` WHERE nature = :sNature")
    List<ConsoData> getAll(String sNature);

    /**
     * Récupération des codes-barres de produits d'une certaine nature.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNature Nature recherchée
     * @return Liste des codes-barres de la base de données ayant la même nature que celle
     * passée en paramètre
     */
    @Query("SELECT code_barres FROM `table` WHERE nature = :sNature")
    List<String> getCodeBarres(String sNature);

    /**
     * Récupération des produits d'une certaine nature et d'un certain numéro de jour.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNature Nature recherchée
     * @param sNumeroJour Numéro de jour recherché
     * @return Liste des produits de la base de données ayant la même nature et le même
     * numéro de jour que ceux passés en paramètres
     */
    @Query("SELECT * FROM `table` WHERE nature = :sNature " +
            "AND numero_jour = :sNumeroJour")
    List<ConsoData> getConsoJour(String sNature, int sNumeroJour);

    /**
     * Récupération des produits d'une certaine nature et d'un certain numéro de semaine.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNature Nature recherchée
     * @param sNumeroSemaine Numéro de semaine recherché
     * @return Liste des produits de la base de données ayant la même nature et le même
     * numéro de semaine que ceux passés en paramètres
     */
    @Query("SELECT * FROM `table` WHERE nature = :sNature " +
            "AND numero_semaine = :sNumeroSemaine")
    List<ConsoData> getConsoSemaine(String sNature, int sNumeroSemaine);

    /**
     * Récupération des produits d'une certaine nature et d'un certain numéro de mois.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNature Nature recherchée
     * @param sNumeroMois Numéro de mois recherché
     * @return Liste des produits de la base de données ayant la même nature et le même
     * numéro de mois que ceux passés en paramètres
     */
    @Query("SELECT * FROM `table` WHERE nature = :sNature " +
            "AND numero_mois = :sNumeroMois")
    List<ConsoData> getConsoMois(String sNature, int sNumeroMois);

    /**
     * Suppression des produits d'une certaine nature et ayant un numéro de mois inférieur au
     * numéro spécifié. (NE FONCTIONNE PAS POUR LE MOIS DE JANVIER)
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNumeroMois Numéro de mois
     * @param sNature Nature recherchée
     */
    @Query("DELETE FROM `table` WHERE numero_mois < :sNumeroMois AND nature = :sNature")
    void consoMajDB(int sNumeroMois, String sNature);

    /**
     * (UNIQUEMENT POUR JANVIER)
     * <br>Suppression des produits d'une certaine nature et ayant un numéro de mois inférieur à
     * sNumeroMois et supérieur à sNumeroMois2.
     * <br>Liste des natures possibles :
     * <ul><li>"consommation"</li>
     *     <li>"historique"</li>
     *     <li>"favoris"</li>
     *     <li>"liste"</li>
     * </ul>
     * @param sNumeroMois Numéro de mois
     * @param sNumeroMois2 Numéro de mois
     * @param sNature Nature recherchée
     */
    @Query("DELETE FROM `table` WHERE numero_mois < :sNumeroMois " +
            "AND numero_mois > :sNumeroMois2 " +
            "AND nature = :sNature")
    void consoMajDBJan(int sNumeroMois, int sNumeroMois2, String sNature);

    /**
     * Supression d'un produit ayant le code-barres spécifié.
     * @param sCode_barres Code-barres du produit à supprimer
     */
    @Query("DELETE FROM `table` WHERE code_barres = :sCode_barres")
    void deleteCode(String sCode_barres);
}