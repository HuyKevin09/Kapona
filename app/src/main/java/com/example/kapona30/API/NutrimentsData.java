package com.example.kapona30.API;

import com.google.gson.annotations.SerializedName;

/**
 * Recueille les différentes données contenues dans le JSON object "nutriments" lui-même contenu
 * dans le JSON object "product".
 * <br>Cette classe doit être utilisée avec {@link com.example.kapona30.API.ProductData}.
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 * </ul>
 */
public class NutrimentsData {

    @SerializedName("fat_100g") //Matières grasses/Lipides
    private float fat;

    @SerializedName("sodium_100g")
    private float sodium;

    @SerializedName("energy-kcal_100g")
    private float energy;

    @SerializedName("salt_100g")
    private float salt;

    @SerializedName("sugars_100g")
    private float sugars;

    @SerializedName("proteins_100g")
    private float proteins;

    @SerializedName("carbohydrates_100g") //Glucides
    private float carbohydrates;

    @SerializedName("saturated-fat_100g") //Acides Gras Saturés
    private float saturatedFat;

    @SerializedName("calcium_100g")
    private float calcium;

    @SerializedName("fiber_100g")
    private float fibers;

    public float getFat(){
        return fat;
    }

    public float getSodium() {
        return sodium;
    }

    public float getEnergy(){
        return energy;
    }

    public float getSalt() {
        return salt;
    }

    public float getSugars(){
        return sugars;
    }

    public float getProteins(){
        return proteins;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getSaturatedFat(){
        return saturatedFat;
    }

    public float getCalcium(){
        return calcium;
    }

    public float getFibers(){
        return fibers;
    }
}
