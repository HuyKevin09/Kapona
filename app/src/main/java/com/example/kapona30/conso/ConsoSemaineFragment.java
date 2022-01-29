package com.example.kapona30.conso;

import com.example.kapona30.database.ConsoData;
import com.example.kapona30.MainActivity;

import java.util.List;

/**
 * Fragment permettant l'affichage du bilan de la consommation par semaine.
 * <br>Il est appelé par {@link com.example.kapona30.conso.ConsommationFragment}.
 * <br>Ce fragment prend toutes les données de la base de données dont la nature correspond à
 * "consommation" et dont la semaine de l'année correspond à celle de {@link com.example.kapona30.MainActivity}.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoJourFragment}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.conso.ConsommationFragment}</li>
 * </ul>
 */
public class ConsoSemaineFragment extends ConsoJourFragment {

    /**
     * Redéfinition de la méthode recuperationDonneesConso() afin de prendre en compte les
     * produits consommés durant la semaine
     * @return Liste de produits
     */
    @Override
    public List<ConsoData> recuperationDonneesConso() {
        return MainActivity.database
                .consoDao()
                .getConsoSemaine("consommation", MainActivity.weekNumber);
    }

    /**
     * Redéfinition de setTitre() afin de nommer correctement la page
     * @return Titre de la page
     */
    @Override
    public String setTitre() {
        return "Consommation Hebdomadaire";
    }
}