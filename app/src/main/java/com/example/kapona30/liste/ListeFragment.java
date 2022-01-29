package com.example.kapona30.liste;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kapona30.database.ConsoData;
import com.example.kapona30.MainActivity;
import com.example.kapona30.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant l'affichage des produits ayant été ajoutés à la liste.
 * <br>Ce fragment est accessible via la barre de menu du bas.
 * <br>Il affiche toutes les données de la base de données dont la nature correspond à
 * "liste".
 * <br>Un bouton permet de supprimer immédiatement tous les produits affichés de la base de données.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.liste.AdapterListe}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 */
public class ListeFragment extends Fragment {

    /**
     * Liste des produits qui seront affichés dans le RecyclerView
     */
    private List<ConsoData> dataList = new ArrayList<>();

    /**
     * Adapter à utiliser
     */
    private AdapterListe adapter;

    /**
     * Mise en page et création du recyclerView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return RecyclerView avec les données affichées
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Met en place la mise en page du fragment
        View view = inflater.inflate(R.layout.fragment_listes_produits, container, false);

        Button btReset = view.findViewById(R.id.bt_reset);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        dataList = MainActivity.database.consoDao().getAll("liste");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterListe(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

        btReset.setOnClickListener(v -> {
            MainActivity.database.consoDao().reset(dataList);
            dataList.clear();
            dataList.addAll(MainActivity.database.consoDao().getAll("liste"));
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}