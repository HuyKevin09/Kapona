package com.example.kapona30.historique;

import android.app.Activity;

import com.example.kapona30.database.ConsoData;
import com.example.kapona30.MainActivity;
import com.example.kapona30.API.Product;
import com.example.kapona30.conso.MainAdapter;

import java.util.List;

/**
 * Adapter pour le recyclerView des produits de l'historique.
 * <br>Cette classe hérite de {@link com.example.kapona30.conso.MainAdapter}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.historique.HistoriqueFragment}</li>
 * </ul>
 */
public class AdapterHistorique extends MainAdapter {
    public AdapterHistorique(Activity context, List<ConsoData> dataList) {
        super(context, dataList);
    }

    /**
     * Texte à afficher sur les cardviews
     * @param produit JSON object "product" du produit concerné
     * @param data Données du produit dans la base de données
     * @return Le texte à afficher sur les cardviews
     */
    @Override
    public String afficheTexte(Product produit, ConsoData data) {
        if (produit.getProduct().getNomProd() == null)
            return "Nom indisponible" +
                    "\nScanné il y a " + (MainActivity.dayNumber-data.getNumeroJour()) + " jour(s)";
        else
            return produit.getProduct().getNomProd() +
                    "\nScanné il y a " + (MainActivity.dayNumber-data.getNumeroJour()) + " jour(s)";
    }
}
