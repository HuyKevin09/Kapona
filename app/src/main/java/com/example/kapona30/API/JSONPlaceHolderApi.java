package com.example.kapona30.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Permet de retrouver les données des produits dans l'API JSON d'OpenFoodFacts en spécifiant
 * le code-barres, qui sera placé dans l'url.
 * <br>Conformément aux demandes d'OpenFoodFacts sur le wiki de l'API, un header avec le nom de
 * l'application est ajouté à la requête.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *   <li>{@link com.example.kapona30.API.Product}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 * </ul>
 */

public interface JSONPlaceHolderApi {

    @Headers("UserAgent: Kapona - Android - Version 1.0")
    @GET("product/{codeBarres}.json")
    Call<Product> getProduct(@Path("codeBarres") String codeBarres);
}