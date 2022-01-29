package com.example.kapona30;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kapona30.conso.ConsommationFragment;
import com.example.kapona30.database.ConsoData;
import com.example.kapona30.database.RoomDB;
import com.example.kapona30.favoris.FavorisFragment;
import com.example.kapona30.historique.HistoriqueFragment;
import com.example.kapona30.liste.ListeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Activité principale.
 * <br>Gère les différents fragments constituant l'application via la barre de menu du bas.
 * <br>Elle initialise la base de données.
 * <br>Cette classe s'occupe également de la fonction de scan. Lorsqu'un produit est scanné,
 * l'activité {@link com.example.kapona30.ActivityProduit} est ouverte.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.conso.ConsommationFragment}</li>
 *     <li>{@link com.example.kapona30.favoris.FavorisFragment}</li>
 *     <li>{@link com.example.kapona30.historique.HistoriqueFragment}</li>
 *     <li>{@link com.example.kapona30.liste.ListeFragment}</li>
 *     <li>{@link com.example.kapona30.ScanFragment}</li>
 *     <li>{@link com.example.kapona30.CaptureAct}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.DialogConso}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoMoisFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoSemaineFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoProdFragment}</li>
 *     <li>{@link com.example.kapona30.conso.MainAdapter}</li>
 *     <li>{@link com.example.kapona30.conso.ConsommationFragment}</li>
 *     <li>{@link com.example.kapona30.favoris.FavorisFragment}</li>
 *     <li>{@link com.example.kapona30.historique.HistoriqueFragment}</li>
 *     <li>{@link com.example.kapona30.liste.ListeFragment}</li>
 * </ul>
 * <br><br>Les variables externes :
 * <ul>
 *     <li>CODE_BARRES</li>
 *     <li>database</li>
 *     <li>fragmentManager</li>
 *     <li>calendar</li>
 *     <li>dayNumber</li>
 *     <li>weekNumber</li>
 *     <li>monthNumber</li>
 * </ul>
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Code-barres à transférer vers {@link com.example.kapona30.ActivityProduit}
     */
    public static final String CODE_BARRES = null;

    /**
     * Base de données où sont stockés les produits enregistrés dans l'historique et ceux
     * ajoutés aux favoris, à la liste et à la consommation.
     * <br>Plus de détails dans {@link com.example.kapona30.database.RoomDB},
     * {@link com.example.kapona30.database.ConsoData} et {@link com.example.kapona30.database.ConsoDao}
     */
    public static RoomDB database;

    /**
     * Gestion des fragments accessibles dans {@link com.example.kapona30.conso.ConsommationFragment}
     */
    public static FragmentManager fragmentManager;

    /**
     * Permet d'accéder au calendrier afin de pouvoir gérer les consommations
     */
    public static Calendar calendar = GregorianCalendar.getInstance(Locale.FRANCE);

    /**
     * Semaine de l'année
     */
    public static int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);

    /**
     * Jour de l'année
     */
    public static int dayNumber = calendar.get(Calendar.DAY_OF_YEAR);

    /**
     * Mois de l'année
     */
    public static int monthNumber = calendar.get(Calendar.MONTH);

    /**
     * Mise en place de la page principale
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mise en place de la barre de navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new ConsommationFragment()).commit();

        //Initialisation de la base de données et mise à jour des produits consommés
        //en supprimant les données obsolètes
        database = RoomDB.getInstance(this);
        if (monthNumber >= 1)
            database.consoDao().consoMajDB(monthNumber - 1, "consommation");
        else //monthNumber == 0 (correspond au mois de Janvier)
            database.consoDao().consoMajDBJan(11, 0, "consommation");
    }

    /**
     * Mise en place de l'activité pour la fonction de scan
     */
    public void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);

        //Orientation de l'ecran bloqué en portrait
        integrator.setOrientationLocked(true);

        //Scanne uniquement les codes-barres de produits alimentaires
        //(UPC A, UPC E, EAN 8, EAN 13 et RSS 14)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);

        //Texte affiché sur le bas de l'écran lors du scan
        integrator.setPrompt("Scannez le code barre de votre produit");

        integrator.initiateScan();
    }

    /**
     * Résultat de l'activité
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Obtention du résultat du scan
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //Si le scan s'est bien passé, affiche la fiche du produit
        if (result.getContents() != null) {
            openActivityProduit(result.getContents());
        }
        else{
            //Si rien n'a été scanné, affiche un message "Aucun résultat"
            Toast.makeText(this, "Aucun résultat", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Ajout dans l'historique et ouverture de la page d'information {@link com.example.kapona30.ActivityProduit}
     * du produit scanné
     * @param codeBarres Code-barres du produit dont on souhaite afficher les informations
     */
    public void openActivityProduit(String codeBarres) {
        Intent intent = new Intent(this, ActivityProduit.class);

        //Affecte le codeBarres passé en paramètre à la constante CODE_BARRES de MainActivity afin
        //de pouvoir la passer à ActivityProduit
        intent.putExtra(CODE_BARRES, codeBarres);

        //Crée une nouvelle donnée de nature "historique" à insérer dans la base de données
        //La quantité et les numéros de jour/semaine/mois n'ont pas d'importance. Ils permettent
        //juste de remplir toutes les colonnes de la base de données
        ConsoData consoDataHisto = new ConsoData();
        consoDataHisto.setQuantite(0.0);
        consoDataHisto.setCodeBarres(codeBarres);
        consoDataHisto.setNature("historique");
        consoDataHisto.setNumeroJour(MainActivity.dayNumber);
        consoDataHisto.setNumeroSemaine(MainActivity.weekNumber);
        consoDataHisto.setNumeroMois(MainActivity.monthNumber);
        MainActivity.database.consoDao().insert(consoDataHisto);

        //Ouverture de la nouvelle activité
        startActivity(intent);
    }

    /**
     * Barre de menu du bas
     */
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod =
            item -> {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_consommation :
                        fragment = new ConsommationFragment();
                        break;

                    case R.id.action_liste :
                        fragment = new ListeFragment();
                        break;

                    case R.id.action_scan :
                        fragment = new ScanFragment();
                        //Active le scan
                        scanCode();
                        break;

                    case R.id.action_historique :
                        fragment = new HistoriqueFragment();
                        break;

                    case R.id.action_favoris :
                        fragment = new FavorisFragment();
                        break;
                }
                //Ouvre le menu sélectionné
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();
                return true;
            };

    /**
     * Création de la barre de menu du haut et mise en place de la fonction de recerche par
     * code-barres
     * @param menu Menu de la barre du haut
     * @return Un booléen
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);

        //Permet de rechercher manuellement un code-barres
        MenuItem menuSearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setQueryHint("Entrez le code-barres");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Lorsque l'on soumet la recherche, ouvre ActivityProduit en passant en paramètre
            //le code-barres entré
            @Override
            public boolean onQueryTextSubmit(String query) {
                String codeBarre = searchView.getQuery().toString();
                openActivityProduit(codeBarre);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /**
     * Gestion des boutons du menu de la barre du haut
     * @param item option sélectionnée dans la barre de menu
     * @return Un booléen
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allergenes:
                //Ouvre l'activité Allergenes
                Intent intent = new Intent(this, Allergenes.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}