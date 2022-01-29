package com.example.kapona30;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

/**
 * Classe permettant d'enregistrer les allergies de l'utilisateur dans SharedPreferences.
 * <br>Accessible via la barre de menu du haut.
 */

public class Allergenes extends AppCompatActivity {

    /**
     * Mise en place de la page
     * <br>A l'ouverture de la page, chaque checkbox est cochée ou décochée en fonction de la
     * valeur de la clé dans SharedPreferences.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergenes);

        CheckBox arachides = findViewById(R.id.checkbox_arachides);
        CheckBox celeri = findViewById(R.id.checkbox_celeri);
        CheckBox crustaces = findViewById(R.id.checkbox_crustaces);
        CheckBox fruitsCoques = findViewById(R.id.checkbox_fruits_a_coque);
        CheckBox gluten = findViewById(R.id.checkbox_gluten);
        CheckBox sesame = findViewById(R.id.checkbox_graines_de_sesame);
        CheckBox lait = findViewById(R.id.checkbox_lait);
        CheckBox lupin = findViewById(R.id.checkbox_lupin);
        CheckBox mollusques = findViewById(R.id.checkbox_mollusques);
        CheckBox moutarde = findViewById(R.id.checkbox_moutarde);
        CheckBox oeufs = findViewById(R.id.checkbox_oeufs);
        CheckBox poissons = findViewById(R.id.checkbox_poissons);
        CheckBox soja = findViewById(R.id.checkbox_soja);

        //Coche ou décoche les checkbox en fonctions de leur valeur dans SharedPreferences
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:peanuts", false);
        arachides.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:celery", false);
        celeri.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:crustaceans", false);
        crustaces.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:nuts", false);
        fruitsCoques.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:gluten", false);
        gluten.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:sesame-seeds", false);
        sesame.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:milk", false);
        lait.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:lupin", false);
        lupin.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:molluscs", false);
        mollusques.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:mustard", false);
        moutarde.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:eggs", false);
        oeufs.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:fish", false);
        poissons.setChecked(checked);

        checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("en:soybeans", false);
        soja.setChecked(checked);
    }

    /**
     * Change la valeur (true/false) de la clé, dans SharedPreferences, dont la checkbox est cliquée
     * @param view Checkbox cliquée
     */
    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkbox_arachides:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:peanuts", checked).apply();
                break;
            case R.id.checkbox_celeri:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:celery", checked).apply();
                break;
            case R.id.checkbox_crustaces:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:crustaceans", checked).apply();
                break;
            case R.id.checkbox_fruits_a_coque:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:nuts", checked).apply();
                break;
            case R.id.checkbox_gluten:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:gluten", checked).apply();
                break;
            case R.id.checkbox_graines_de_sesame:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:sesame-seeds", checked).apply();
                break;
            case R.id.checkbox_lait:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:milk", checked).apply();
                break;
            case R.id.checkbox_lupin:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:lupin", checked).apply();
                break;
            case R.id.checkbox_mollusques:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:molluscs", checked).apply();
                break;
            case R.id.checkbox_moutarde:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:mustard", checked).apply();
                break;
            case R.id.checkbox_oeufs:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:eggs", checked).apply();
                break;
            case R.id.checkbox_poissons:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:fish", checked).apply();
                break;
            case R.id.checkbox_soja:
                PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit()
                        .putBoolean("en:soybeans", checked).apply();
                break;
        }
    }
}