package com.example.kapona30.conso;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kapona30.API.JSONPlaceHolderApi;
import com.example.kapona30.API.Product;
import com.example.kapona30.ActivityProduit;
import com.example.kapona30.R;
import com.example.kapona30.database.ConsoData;
import com.example.kapona30.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment permettant l'affichage du bilan de la consommation par jour.
 * <br>Il est appelé par {@link com.example.kapona30.conso.ConsommationFragment}.
 * <br>Ce fragment prend toutes les données de la base de données dont la nature correspond à
 * "consommation" et dont le jour de l'année correspond à celui de {@link com.example.kapona30.MainActivity}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.API.JSONPlaceHolderApi}</li>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 *     <li>{@link com.example.kapona30.API.NutrimentsData}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.conso.ConsoMoisFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoSemaineFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsommationFragment}</li>
 * </ul>
 */

public class ConsoJourFragment extends Fragment {

    /**
     * Diagramme de répartition des nutriments
     */
    private HorizontalBarChart barre;

    /**
     * Energie apportée par les produits
     */
    private float energie;

    /**
     * Quantité de matières grasses
     */
    private float matieresGrasses;

    /**
     * Quantité d'cides gras saturés
     */
    private float acidesGras;

    /**
     * Quantité de glucides
     */
    private float glucides;

    /**
     * Quantité de sel
     */
    private float sel;

    /**
     * Quantité de sucres
     */
    private float sucres;

    /**
     * Quantité de protéines
     */
    private float proteines;

    /**
     * Quantité de sodium
     */
    private float sodium;

    /**
     * Quantité de fibres
     */
    private float fibres;

    /**
     * Quantité de calcium
     */
    private float calcium;

    /**
     * Energie apportée par les produits
     */
    private TextView textEnergy;

    /**
     * Quantité de matières grasses
     */
    private TextView textFat;

    /**
     * Quantité d'cides gras saturés
     */
    private TextView textSaturatedFat;

    /**
     * Quantité de glucides
     */
    private TextView textCarbohydrates;

    /**
     * Quantité de sel
     */
    private TextView textSalt;

    /**
     * Quantité de sucres
     */
    private TextView textSugars;

    /**
     * Quantité de protéines
     */
    private TextView textProteins;

    /**
     * Quantité de sodium
     */
    private TextView textSodium;

    /**
     * Quantité de calcium
     */
    private TextView textCalcium;

    /**
     * Quantité de fibres
     */
    private TextView textFibers;

    /**
     * Mise en place de la page
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return La page à afficher
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Met en place la mise en page du fragment
        View view = inflater.inflate(R.layout.fragment_conso_bilans, container, false);

        //Titre de la page
        TextView titre = view.findViewById(R.id.conso);
        String titrePage = setTitre();
        titre.setText(titrePage);

        //Récupération des consommations
        List<ConsoData> consoData = recuperationDonneesConso();

        //Mise en place des zones de texte et du diagramme
        textEnergy = view.findViewById(R.id.energy);
        textFat = view.findViewById(R.id.fat);
        textSaturatedFat = view.findViewById(R.id.saturated_fat);
        textCarbohydrates = view.findViewById(R.id.carbohydrates);
        textSalt = view.findViewById(R.id.salt);
        textSugars = view.findViewById(R.id.sugars);
        textProteins = view.findViewById(R.id.proteins);
        textSodium = view.findViewById(R.id.sodium);
        textCalcium = view.findViewById(R.id.calcium);
        textFibers = view.findViewById(R.id.fibers);
        barre = view.findViewById(R.id.barre_horizontale);

        //Récupération des données de chacun des produits consommés afin de procéder au calcul
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fr.openfoodfacts.org/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        for(ConsoData conso : consoData){
            double quantite = conso.getQuantite();
            Call<Product> call = jsonPlaceHolderApi.getProduct(conso.getCodeBarres());

            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(@NonNull Call<Product> call,
                                       @NonNull Response<Product> response) {
                    if (!response.isSuccessful()) {
                        int codeReponse = response.code();
                        titre.setText(codeReponse);
                        return;
                    }
                    Product produit = response.body();
                    calculConso(produit, quantite);
                    displayUpdatedData();
                }

                @Override
                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                    titre.setText(t.getMessage());
                }
            });
        }
        return view;
    }

    /**
     * Titre à afficher
     * @return Titre de la page
     */
    public String setTitre() {
        return "Consommation Journalière";
    }

    /**
     * Récupère les produits consommés en ce jour
     * @return Liste de produits
     */
    public List<ConsoData> recuperationDonneesConso() {
        return MainActivity.database
                .consoDao()
                .getConsoJour("consommation", MainActivity.dayNumber);
    }

    /**
     * Affiche les données sur la page.
     * <br>Les valeurs sont arrondies au centième
     */
    public void displayUpdatedData() {
        String text = "Energie totale : " + Math.round(energie * 100.0) / 100.0 + "kcal";
        textEnergy.setText(text);

        text = "Matières grasses\n" + Math.round(matieresGrasses * 100.0) / 100.0 + "g";
        textFat.setText(text);

        text = "dont acides gras saturés\n" + Math.round(acidesGras * 100.0) / 100.0 + "g";
        textSaturatedFat.setText(text);

        text = "Glucides\n" + Math.round(glucides * 100.0) / 100.0 + "g";
        textCarbohydrates.setText(text);

        text = "Sel\n" + Math.round(sel * 100.0) / 100.0 + "g";
        textSalt.setText(text);

        text = "dont sucres\n" + Math.round(sucres * 100.0) / 100.0 + "g";
        textSugars.setText(text);

        text = "Protéines\n" + Math.round(proteines * 100.0) / 100.0 + "g";
        textProteins.setText(text);

        text = "Sodium\n" + Math.round(sodium * 100.0) / 100.0 + "g";
        textSodium .setText(text);

        text = "Fibres\n" + Math.round(fibres * 100.0) / 100.0 + "g";
        textFibers .setText(text);

        text = "Calcium\n" + Math.round(calcium * 100.0) / 100.0 + "g";
        textCalcium .setText(text);

        //Création du diagramme
        BarDataSet barDataSet = new BarDataSet(dataValues(matieresGrasses, glucides, fibres,
                proteines, sel, sodium, calcium), "");
        barDataSet.setColors(ActivityProduit.colorClassArray);

        BarData barData = new BarData(barDataSet);
        barre.setData(barData);

        ArrayList<String> xAxisName = new ArrayList<>();
        barchart(barre,dataValues(matieresGrasses, glucides, fibres, proteines, sel, sodium,
                calcium),xAxisName, ActivityProduit.colorClassArray);

        //Mise à jour du diagramme
        barData.notifyDataChanged();
        barre.notifyDataSetChanged();
        barre.invalidate();
    }

    /**
     * Calcul de la quantité des nutriments consommés
     * @param produit Produit consommé
     * @param quantite Quantité consommée
     */
    public void calculConso(Product produit, double quantite) {
        //Les quantités de nutriments correspondent aux apports pour 100g, d'où la division par 100
        energie += produit.getProduct().getNutrimentsData().getEnergy() * quantite/100;
        matieresGrasses += produit.getProduct().getNutrimentsData().getFat() * quantite/100;
        acidesGras += produit.getProduct().getNutrimentsData().getSaturatedFat() * quantite/100;
        glucides += produit.getProduct().getNutrimentsData().getCarbohydrates() * quantite/100;
        sel += produit.getProduct().getNutrimentsData().getSalt() * quantite/100;
        sucres += produit.getProduct().getNutrimentsData().getSugars() * quantite/100;
        proteines += produit.getProduct().getNutrimentsData().getProteins() * quantite/100;
        sodium += produit.getProduct().getNutrimentsData().getSodium() * quantite/100;
        fibres += produit.getProduct().getNutrimentsData().getFibers() * quantite/100;
        calcium += produit.getProduct().getNutrimentsData().getCalcium() * quantite/100;
    }

    /**
     * Permet de définir les valeurs pour le diagramme
     * @param matieresGrasses Quantité de matières grasses dans le produit affiché
     * @param glucides Quantité de glucides dans le produit affiché
     * @param fibres Quantité de fibres dans le produit affiché
     * @param proteines Quantité de protéines dans le produit affiché
     * @param sel Quantité de sel dans le produit affiché
     * @param sodium Quantité de sodium dans le produit affiché
     * @param calcium Quantité de calcium dans le produit affiché
     * @return ArrayList de données destinées à la création d'une BarChart
     * (classes de la librairie MPAndroidChart de PhilJay)
     */
    public ArrayList<BarEntry> dataValues(float matieresGrasses, float glucides, float fibres,
                                           float proteines, float sel, float sodium,
                                           float calcium) {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0, new float[]{matieresGrasses, glucides, fibres, proteines,
                sel, sodium, calcium}));
        return dataVals;
    }

    /**
     * Création du diagramme et réglages des différentes options d'affichage
     * @param barChart Barre à utiliser pour tracer le diagramme
     * @param arrayList ArrayList de données destinées à la création d'une BarChart
     *                  (classes de la librairie MPAndroidChart de PhilJay)
     * @param xAxisValues Valeurs de l'axe des abscisses
     * @param couleurs Tableau d'entiers indiquant les couleurs à utiliser
     */
    public void barchart(BarChart barChart, ArrayList<BarEntry> arrayList,
                         final ArrayList<String> xAxisValues, int[] couleurs) {
        BarDataSet barDataSet = new BarDataSet(arrayList, null);
        barDataSet.setColors(couleurs);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(10f);
        barData.setValueTextSize(0f);
        barChart.setBackgroundColor(Color.TRANSPARENT);
        barChart.setDrawGridBackground(false);

        //Cacher la légende
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        //Cacher les axes X et Y
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        //Cacher la description du diagramme
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);

        //Empêcher les interactions avec le diagramme
        barChart.setTouchEnabled(false);
    }
}