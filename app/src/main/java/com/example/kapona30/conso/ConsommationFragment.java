package com.example.kapona30.conso;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kapona30.MainActivity;
import com.example.kapona30.R;

/**
 * Menu "Consommation", accessible via le menu de la barre du bas.
 * <br>Gère les différents fragments permettant de faire un bilan de la consommation par jour,
 * semaine ou mois ainsi que le fragment permettant de voir les produits consommés.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoSemaineFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoMoisFragment}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoProdFragment}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 */

public class ConsommationFragment extends Fragment implements View.OnClickListener{

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
        View view = inflater.inflate(R.layout.fragment_consommation, container, false);

        Button consoJour = view.findViewById(R.id.button_jour);
        consoJour.setOnClickListener(this);

        Button consoSemaine = view.findViewById(R.id.button_semaine);
        consoSemaine.setOnClickListener(this);

        Button consoMois = view.findViewById(R.id.button_mois);
        consoMois.setOnClickListener(this);

        Button consoProduits = view.findViewById(R.id.button_prod_conso);
        consoProduits.setOnClickListener(this);

        return view;
    }

    /**
     * Appel des différents fragments en fonction du bouton appuyé
     * @param v Bouton appuyé
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_jour:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container
                        , new ConsoJourFragment()).commit();
                break;

            case R.id.button_semaine:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container
                        , new ConsoSemaineFragment()).commit();
                break;

            case R.id.button_mois:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container
                        , new ConsoMoisFragment()).commit();
                break;

            case R.id.button_prod_conso:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container
                        , new ConsoProdFragment()).commit();
                break;
        }
    }
}