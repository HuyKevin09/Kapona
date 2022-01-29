package com.example.kapona30.favoris;

import android.app.Activity;

import com.example.kapona30.API.Product;
import com.example.kapona30.conso.MainAdapter;
import com.example.kapona30.database.ConsoData;

import java.util.List;

/**
 * Adapter pour le recyclerView des produits favoris.
 * <br>Cette classe hérite de {@link com.example.kapona30.conso.MainAdapter}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.favoris.FavorisFragment}</li>
 * </ul>
 */
public class AdapterFavoris extends MainAdapter {
    public AdapterFavoris(Activity context, List<ConsoData> dataList) {
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
            return "Nom indisponible";
        else
            return produit.getProduct().getNomProd();
    }
}