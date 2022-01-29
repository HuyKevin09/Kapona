package com.example.kapona30.favoris;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapona30.MainActivity;
import com.example.kapona30.R;
import com.example.kapona30.database.ConsoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant l'affichage des produits ayant été ajoutés aux favoris.
 * <br>Ce fragment est accessible via la barre de menu du bas.
 * <br>Il affiche toutes les données de la base de données dont la nature correspond à
 * "favoris".
 * <br>Un bouton permet de supprimer immédiatement tous les produits affichés de la base de données.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.favoris.AdapterFavoris}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 * </ul>
 */
public class FavorisFragment extends Fragment {

    /**
     * Liste des produits qui seront affichés dans le RecyclerView
     */
    private List<ConsoData> dataList = new ArrayList<>();

    /**
     * Adapter à utiliser
     */
    private AdapterFavoris adapter;

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

        dataList = MainActivity.database.consoDao().getAll("favoris");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AdapterFavoris(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

        btReset.setOnClickListener(v -> {
            MainActivity.database.consoDao().reset(dataList);
            dataList.clear();
            dataList.addAll(MainActivity.database.consoDao().getAll("favoris"));
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}