package com.example.kapona30;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.kapona30.API.JSONPlaceHolderApi;
import com.example.kapona30.API.Product;
import com.example.kapona30.database.ConsoData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cette classe permet l'affichage des informations relatives au produit dont le code-barres
 * a permis de démarrer cette activité.
 * <br>A travers cette classe, il est possible d'ajouter un produit aux favoris, à la liste ou
 * à la consommation.
 * <br>Elle crée également une alerte si l'utilisateur est allergique à l'un des constituant
 * du produit.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.DialogConso}</li>
 *     <li>{@link com.example.kapona30.API.JSONPlaceHolderApi}</li>
 *     <li>{@link com.example.kapona30.API.NutrimentsData}</li>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.DialogConso}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 * </ul>
 * <br><br>Les variables externes :
 * <ul>
 *     <li>colorClassArray</li>
 * </ul>
 */

public class ActivityProduit extends AppCompatActivity {

    /**
     * Nom du produit
     * <br>S'il y a une erreur lors de l'appel de l'API, le message d'erreur apparaîtra à la
     * place du nom du produit
     */
    private TextView textName;

    /**
     * Nutriscore du produit
     */
    private TextView textNutriscore;

    /**
     * Désigne la quantité pour laquelle les quantités de nutriments sont définies
     */
    private TextView textQuantite;

    /**
     * Energie apportée par le produit
     */
    private TextView textEnergy;

    /**
     * Quantité de matières grasses du produit
     */
    private TextView textFat;

    /**
     * Quantité d'cides gras saturés du produit
     */
    private TextView textSaturatedFat;

    /**
     * Quantité de glucides du produit
     */
    private TextView textCarbohydrates;

    /**
     * Quantité de sel du produit
     */
    private TextView textSalt;

    /**
     * Quantité de sucres du produit
     */
    private TextView textSugars;

    /**
     * Quantité de protéines du produit
     */
    private TextView textProteins;

    /**
     * Quantité de sodium du produit
     */
    private TextView textSodium;

    /**
     * Quantité de calcium du produit
     */
    private TextView textCalcium;

    /**
     * Quantité de fibres du produit
     */
    private TextView textFibers;

    /**
     * Ingrédients du produit
     */
    private TextView textIngredients;

    /**
     * Image du produit
     */
    private ImageView imageView;

    /**
     * Code-barres du produit ayant permis d'ouvrir cette activité
     */
    private String codeBarres;

    /**
     * Diagramme de répartition des nutriments
     */
    private HorizontalBarChart barre;

    /**
     * Couleurs à utiliser pour le diagramme
     */
    public static final int[] colorClassArray = new int[]{Color.YELLOW, Color.MAGENTA, Color.GREEN,
            Color.RED, Color.GRAY, Color.GRAY, Color.CYAN};

    /**
     * Mise en place des champs textuels et des boutons, récupération des données du produit depuis
     * l'API, affichage éventuelle d'une alerte d'allergènes et création du diagramme avec les
     * valeurs récupérées
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        //Permet d'obtenir le code-barres scanné dans MainActivity ou celui du produit sur
        //lequel on a appuyé dans l'un des menus (historique/favoris/consommation/liste)
        Intent intent = getIntent();
        codeBarres = intent.getStringExtra(MainActivity.CODE_BARRES);

        textName = findViewById(R.id.product_name);
        textNutriscore = findViewById(R.id.nutriscore);
        textQuantite = findViewById(R.id.quantite);
        textEnergy = findViewById(R.id.energy);
        textFat = findViewById(R.id.fat);
        textSaturatedFat = findViewById(R.id.saturated_fat);
        textCarbohydrates = findViewById(R.id.carbohydrates);
        textSalt = findViewById(R.id.salt);
        textSugars = findViewById(R.id.sugars);
        textProteins = findViewById(R.id.proteins);
        textSodium = findViewById(R.id.sodium);
        textCalcium = findViewById(R.id.calcium);
        textFibers = findViewById(R.id.fibers);
        textIngredients = findViewById(R.id.text_ingredients);
        imageView = findViewById(R.id.image_view);

        Button buttonFavoris = findViewById(R.id.button_favoris);
        buttonFavoris.setOnClickListener(v -> {
            //Lors d'un clic sur le bouton favoris :
            //Si le produit est déjà dans les favoris, renvoie un toast, indiquant sa présence
            //et n'enregistre pas le produit dans les favoris une nouvelle fois
            if (MainActivity.database.consoDao().getCodeBarres("favoris")
                    .contains(codeBarres)){
                Toast.makeText(ActivityProduit.this,
                        "Le produit est déjà dans les favoris", Toast.LENGTH_SHORT).show();
            } else {
                //Sinon le produit est inséré dans la base de données
                //Il aura une nature "favoris"
                //La quantité et les numéros de jour/semaine/mois n'ont pas d'importance. Ils
                //permettent juste de remplir toutes les colonnes de la base de données
                //Un toast confirme l'insertion
                ConsoData consoData = new ConsoData();
                consoData.setQuantite(0.0);
                consoData.setCodeBarres(codeBarres);
                consoData.setNature("favoris");
                consoData.setNumeroJour(MainActivity.dayNumber);
                consoData.setNumeroSemaine(MainActivity.weekNumber);
                consoData.setNumeroMois(MainActivity.monthNumber);
                MainActivity.database.consoDao().insert(consoData);
                Toast.makeText(ActivityProduit.this,
                        "Produit ajouté aux favoris", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonConso = findViewById(R.id.button_conso);
        buttonConso.setOnClickListener(v -> openDialogConso());

        //Le bouton d'ajout à la liste a un fonctionnement similaire au bouton favoris
        //La principale différence est que le produit inséré dans la base de données est
        //de nature "liste" ici
        Button buttonListe = findViewById(R.id.button_liste);
        buttonListe.setOnClickListener(v -> {
            if (MainActivity.database.consoDao().getCodeBarres("liste")
                    .contains(codeBarres)){
                Toast.makeText(ActivityProduit.this,
                        "Le produit est déjà dans la liste", Toast.LENGTH_SHORT).show();
            } else {
                ConsoData consoData = new ConsoData();
                consoData.setQuantite(0.0);
                consoData.setCodeBarres(codeBarres);
                consoData.setNature("liste");
                consoData.setNumeroJour(MainActivity.dayNumber);
                consoData.setNumeroSemaine(MainActivity.weekNumber);
                consoData.setNumeroMois(MainActivity.monthNumber);
                MainActivity.database.consoDao().insert(consoData);
                Toast.makeText(ActivityProduit.this,
                        "Produit ajouté à la liste", Toast.LENGTH_SHORT).show();
            }
        });

        //Recherche du produit dans l'API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fr.openfoodfacts.org/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        Call<Product> call = jsonPlaceHolderApi.getProduct(codeBarres);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call,
                                   @NonNull Response<Product> response) {

                //Affiche un message dans le titre de la page au cas où il y aurait un problème
                //Empêche également l'ajout du produit dans la base de données via les boutons
                //en les enlevant. Cela permet d'éviter la propagation d'erreurs
                if(!response.isSuccessful()){
                    int codeReponse = response.code();
                    textName.setText(codeReponse);
                    buttonConso.setVisibility(View.INVISIBLE);
                    buttonFavoris.setVisibility(View.INVISIBLE);
                    buttonListe.setVisibility(View.INVISIBLE);
                    return;
                }

                Product produit = response.body();

                //Vérifie que le produit existe et évite un crash potentiel
                //Efface le produit de la base de données si le code-barres n'est pas valide
                //(Rappel : le produit a été enregistré dans l'historique dans la méthode
                //openActivityProduit() de MainActivity)
                //Empêche également l'ajout du produit dans la base de données via les boutons
                //en les enlevant. Cela permet d'éviter la propagation d'erreurs
                if (produit.getStatus() == 0) {
                    MainActivity.database.consoDao().deleteCode(codeBarres);
                    buttonConso.setVisibility(View.INVISIBLE);
                    buttonFavoris.setVisibility(View.INVISIBLE);
                    buttonListe.setVisibility(View.INVISIBLE);

                    String errorMessage = "Produit introuvable";
                    textName.setText(errorMessage);
                    return;
                }

                //Vérifie la présence d'allergènes et affiche une alerte si nécessaire
                ArrayList<String> allergensList = produit.getProduct().getAllergens();
                StringBuilder stringBuilder = new StringBuilder();
                boolean allergie = false;
                for(String string : allergensList){
                    if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                            .getBoolean(string, false)){
                        stringBuilder.append("\n").append(string);
                        allergie = true;
                    }
                }
                if (allergie)
                    allergensAlert(stringBuilder
                            .toString()
                            .replace("en:gluten", "-Gluten")
                            .replace("en:crustaceans", "-Crustacés")
                            .replace("en:eggs", "-Oeufs")
                            .replace("en:fish", "-Poisson")
                            .replace("en:peanuts", "-Arachides")
                            .replace("en:soybeans", "-Soja")
                            .replace("en:milk","-Lait")
                            .replace("en:nuts", "-Fruits à coques")
                            .replace("en:mustard", "-Moutarde")
                            .replace("en:celery", "-Céleri")
                            .replace("en:sesame-seeds", "-Graines de sésame")
                            .replace("en:lupin", "-Lupin")
                            .replace("en:molluscs", "-Mollusques"));

                //Récupération des différentes données
                String nomProduit;
                if (produit.getProduct().getNomProd() == null)
                    nomProduit = "Nom indisponible";
                else
                    nomProduit = produit.getProduct().getNomProd();

                String nutriscore;
                if (produit.getProduct().getNutritionGrade() == null)
                    nutriscore = "indisponible";
                else
                    nutriscore = produit.getProduct().getNutritionGrade().toUpperCase();

                float matieresGrasses = produit.getProduct().getNutrimentsData().getFat();
                float acidesGras = produit.getProduct().getNutrimentsData().getSaturatedFat();
                float glucides = produit.getProduct().getNutrimentsData().getCarbohydrates();
                float sel = produit.getProduct().getNutrimentsData().getSalt();
                float sucres = produit.getProduct().getNutrimentsData().getSugars();
                float proteines = produit.getProduct().getNutrimentsData().getProteins();
                float sodium = produit.getProduct().getNutrimentsData().getSodium();
                float fibres = produit.getProduct().getNutrimentsData().getFibers();
                float calcium = produit.getProduct().getNutrimentsData().getCalcium();

                //Affichage des différentes données
                textName.append(nomProduit);
                textNutriscore.append("Nutriscore : " + nutriscore);
                textQuantite.append("Apports nutritionnels pour 100g :");
                textEnergy.append("Energie : " + produit.getProduct().getNutrimentsData()
                        .getEnergy() + "kcal");
                textFat.append("Matières grasses\n" + matieresGrasses + "g");
                textSaturatedFat.append("dont acides gras saturés\n" + acidesGras + "g");
                textCarbohydrates.append("Glucides\n" + glucides + "g");
                textSalt.append("Sel\n" + sel + "g");
                textSugars.append("dont sucres\n" + sucres + "g");
                textProteins.append("Protéines\n" + proteines + "g");
                textSodium .append("Sodium\n" + sodium + "g");
                textFibers .append("Fibres\n" + fibres + "g");
                textCalcium .append("Calcium\n" + calcium + "g");
                textIngredients.append("Ingrédients : " + produit.getProduct().getIngredients());
                Picasso.get().load(produit.getProduct().getImageUrl()).into(imageView);

                //Création du diagramme en prenant en compte les données récupérées
                barre = findViewById(R.id.barre_horizontale);
                BarDataSet barDataSet = new BarDataSet(dataValues(matieresGrasses, glucides, fibres,
                        proteines, sel, sodium, calcium), "");
                barDataSet.setColors(colorClassArray);

                BarData barData = new BarData(barDataSet);
                barre.setData(barData);

                ArrayList<String> xAxisName = new ArrayList<>();
                barchart(barre,dataValues(matieresGrasses, glucides, fibres, proteines, sel, sodium,
                        calcium),xAxisName, colorClassArray);
            }

            //S'il y a eu un problème dans l'appel de l'API, même traitement que si le produit
            //n'existait pas
            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                MainActivity.database.consoDao().deleteCode(codeBarres);
                buttonConso.setVisibility(View.INVISIBLE);
                buttonFavoris.setVisibility(View.INVISIBLE);
                buttonListe.setVisibility(View.INVISIBLE);
                textName.setText(t.getMessage());
            }
        });
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

    /**
     * Permet l'ouverture de la boîte de dialogue customisée de la classe
     * {@link com.example.kapona30.DialogConso}.
     * <br>Transfère également le code-barres du produit afin qu'il puisse être inséré dans la
     * base de données, une fois que l'utilisateur aura confirmé sa saisie.
     */
    public void openDialogConso() {
        DialogConso dialogConso = new DialogConso();
        Bundle bundle = new Bundle();
        Intent intent = getIntent();

        //Permet de transférer le code-barres à DialogConso
        String codeBarres = intent.getStringExtra(MainActivity.CODE_BARRES);
        bundle.putString("codeBarres",codeBarres);
        dialogConso.setArguments(bundle);
        dialogConso.show(getSupportFragmentManager(), null);
    }

    /**
     * Permet l'ouverture d'une boîte de dialogue avertissant l'utilisateur de la présence d'un
     * des allergènes sélectionnés dans le menu "Allergènes"
     * @param allergenes Chaîne de caractères contenant les noms des différents allergènes à
     *                   afficher
     */
    public void allergensAlert(String allergenes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ce produit contient un ou des composant(s) auquel(s) vous êtes allergique :\n" + allergenes);
        builder.setTitle("Attention!");
        builder.setNeutralButton("Compris!", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
