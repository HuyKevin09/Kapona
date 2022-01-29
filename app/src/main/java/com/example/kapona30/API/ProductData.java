package com.example.kapona30.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Recueille les différentes données dans le JSON object "product". Permet aussi d'accéder au JSON
 * object "nutriments".
 * <br>Cette classe doit être utilisée avec {@link com.example.kapona30.API.Product}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.API.NutrimentsData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.favoris.AdapterFavoris}</li>
 *     <li>{@link com.example.kapona30.historique.AdapterHistorique}</li>
 *     <li>{@link com.example.kapona30.liste.AdapterListe}</li>
 * </ul>
 */
public class ProductData {

    @SerializedName("product_name_fr")
    private String nomProd;

    /**
     * Code-barres du produit
     */
    @SerializedName("code")
    private String code;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("nutrition_grade_fr")
    private String nutritionGrade;

    /**
     * Liste des allergènes contenus
     */
    @SerializedName("allergens_tags")
    private ArrayList<String> allergens;

    /**
     * JSON object "nutriments" contenant les données nutritives
     */
    @SerializedName("nutriments")
    private NutrimentsData nutrimentsData;

    /**
     * Ingrédients composant le produit
     */
    @SerializedName("ingredients_text_fr")
    private String ingredients;

    public String getNomProd(){
        return nomProd;
    }

    public String getCode() {
        return code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNutritionGrade(){
        return nutritionGrade;
    }

    public NutrimentsData getNutrimentsData(){
        return nutrimentsData;
    }

    public ArrayList<String> getAllergens(){
        return allergens;
    }

    public String getIngredients(){
        return ingredients;
    }
}

