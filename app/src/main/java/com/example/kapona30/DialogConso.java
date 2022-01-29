package com.example.kapona30;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.kapona30.database.ConsoData;

/**
 * Cette classe permet la création d'une boîte de dialogue customisée pour l'ajout d'un produit
 * à la consommation dans l'activité {@link com.example.kapona30.ActivityProduit}.
 * <br>Demande à l'utilisateur d'entrer une quantité puis insère les données dans la base de
 * données.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 * </ul>
 */

public class DialogConso extends AppCompatDialogFragment {

    /**
     * Champ permettant de saisir la quantité consommée
     */
    private EditText editText;

    /**
     * Crée une boîte de dialogue demandant à l'utilisateur de saisir la quantité consommée
     * afin de l'enregistrer dans la base de données.
     * @param savedInstanceState
     * @return Une boîte de dialogue
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        editText = view.findViewById(R.id.quantite_conso);

        //Récupération du code-barres de ActivityProduit
        Bundle bundle = getArguments();
        String codeBarres = bundle.getString("codeBarres","");

        //Initialisation des différents champs de la boîte de dialogue
        builder.setView(view)
                .setTitle("Ajouter un produit à la consommation")
                .setNegativeButton("Annuler", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Valider", (dialog, which) -> {
                    try{
                        //Récupération de la quantité saisie
                        double quantite = Double.parseDouble(editText.getText().toString());

                        //Insertion du produit de nature "consommation" dans la base de données
                        ConsoData consoData = new ConsoData();
                        consoData.setQuantite(quantite);
                        consoData.setCodeBarres(codeBarres);
                        consoData.setNature("consommation");
                        consoData.setNumeroJour(MainActivity.dayNumber);
                        consoData.setNumeroSemaine(MainActivity.weekNumber);
                        consoData.setNumeroMois(MainActivity.monthNumber);
                        MainActivity.database.consoDao().insert(consoData);

                        //Affichage d'un toast indiquant que le produit a bien été enregistré
                        Toast.makeText(getActivity(),"Consommation ajoutée",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e){
                        e.getMessage();
                    }
                });

        return builder.create();
    }
}