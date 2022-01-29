package com.example.kapona30.API;

import com.google.gson.annotations.SerializedName;

/**
 * Permet d'accéder au JSON object "product".
 * <br>Cette classe doit être utilisée avec {@link com.example.kapona30.API.JSONPlaceHolderApi}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.API.JSONPlaceHolderApi}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.historique.AdapterHistorique}</li>
 *     <li>{@link com.example.kapona30.favoris.AdapterFavoris}</li>
 *     <li>{@link com.example.kapona30.liste.AdapterListe}</li>
 * </ul>
 */
public class Product {

    /**
     * JSON object "product"
     */
    @SerializedName("product")
    private ProductData product;

    /**
     * Vaut 1 si le produit existe, 0 sinon
     */
    @SerializedName("status")
    private int status;

    public ProductData getProduct(){
        return product;
    }

    public int getStatus(){
        return status;
    }
}
