package com.example.kapona30.conso;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapona30.API.JSONPlaceHolderApi;
import com.example.kapona30.API.Product;
import com.example.kapona30.ActivityProduit;
import com.example.kapona30.MainActivity;
import com.example.kapona30.R;
import com.example.kapona30.database.ConsoData;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Adapter pour le recyclerView des produits consommés. Le recyclerView est composé de cardviews qui
 * contiennent chacune un bouton permettant d'effacer l'élément de la base de données. Lors d'un clic
 * sur une cardview, la page du produit s'affiche.
 * <br>Cette classe est l'adapter principal. Elle est héritée par les adapters de l'historique, de
 * la liste et des favoris.
 * <br><br>Classes utilisées par cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.database.RoomDB}</li>
 *     <li>{@link com.example.kapona30.database.ConsoDao}</li>
 *     <li>{@link com.example.kapona30.database.ConsoData}</li>
 *     <li>{@link com.example.kapona30.MainActivity}</li>
 *     <li>{@link com.example.kapona30.ActivityProduit}</li>
 *     <li>{@link com.example.kapona30.API.JSONPlaceHolderApi}</li>
 *     <li>{@link com.example.kapona30.API.Product}</li>
 *     <li>{@link com.example.kapona30.API.ProductData}</li>
 * </ul>
 * <br><br>Classes utilisant cette classe :
 * <ul>
 *     <li>{@link com.example.kapona30.favoris.AdapterFavoris}</li>
 *     <li>{@link com.example.kapona30.historique.AdapterHistorique}</li>
 *     <li>{@link com.example.kapona30.liste.AdapterListe}</li>
 *     <li>{@link com.example.kapona30.conso.ConsoProdFragment}</li>
 * </ul>
 * <br><br>Méthodes externes :
 * <ul>
 *     <li>ViewHolder()</li>
 * </ul>
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    /**
     * Liste des produits à afficher
     */
    private final List<ConsoData> dataList;

    /**
     * Activité associée à cet adapter
     */
    private final Activity context;

    /**
     * Constructeur de l'adapter pour le recyclerView
     * @param context Activité associée à cet adapter
     * @param dataList Liste des données à afficher dans le recyclerView
     */
    public MainAdapter(Activity context, List<ConsoData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * Création d'une cardview et définition de sa mise page
     * @param parent
     * @param viewType
     * @return Une cardview
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Création du contenu des cardviews et des interactions possibles
     * @param holder Cardview
     * @param position Position de la cardview dans le recyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConsoData data = dataList.get(position);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fr.openfoodfacts.org/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);

        String codeBarres = data.getCodeBarres();
        Call<Product> call = jsonPlaceHolderApi.getProduct(codeBarres);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call,
                                   @NonNull Response<Product> response) {
                Product produit = response.body();
                String text = afficheTexte(produit, data);
                holder.textView.setText(text);
                Picasso.get().load(produit.getProduct().getImageUrl()).into(holder.productImage);
            }
            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                holder.textView.setText(t.getMessage());
            }
        });

        //Suppression des données associée à une cardview
        holder.btDelete.setOnClickListener(v -> {
            ConsoData d = dataList.get(holder.getAdapterPosition());
            MainActivity.database.consoDao().delete(d);
            int position1 = holder.getAdapterPosition();
            dataList.remove(position1);
            notifyItemRemoved(position1);
            notifyItemRangeChanged(position1, dataList.size());
        });

        //Ouverture de la page du produit en cliquant sur la cardview
        holder.cardview.setOnClickListener(v -> openActivityProduit(data.getCodeBarres()));
    }

    /**
     * Affiche du texte sur les cardviews
     * @param produit JSON object "product" du produit concerné
     * @param data Données du produit dans la base de données
     * @return Nom du produit, quantité consommée et nombre de jour(s) depuis l'ajout
     */
    public String afficheTexte(Product produit, ConsoData data) {
        if (produit.getProduct().getNomProd() == null)
            return "Nom indisponible" +
                    "\nQuantité : " + data.getQuantite() + "g" +
                    "\nAjouté il y a " + (MainActivity.dayNumber-data.getNumeroJour()) + " jour(s)";
        else
            return produit.getProduct().getNomProd() +
                    "\nQuantité : " + data.getQuantite() + "g" +
                    "\nAjouté il y a " + (MainActivity.dayNumber-data.getNumeroJour()) + " jour(s)";
    }

    /**
     * Retourne le nombre d'éléments contenus dans le recyclerView
     * @return Nombre d'éléments contenus dans le recyclerView
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Ouverture de la page d'information {@link com.example.kapona30.ActivityProduit}
     * du produit scanné
     * @param codeBarres code-barres du produit dont on souhaite afficher les informations
     */
    public void openActivityProduit(String codeBarres) {
        Intent intent = new Intent(context, ActivityProduit.class);
        intent.putExtra(MainActivity.CODE_BARRES, codeBarres);
        context.startActivity(intent);
    }

    /**
     * Création des cardviews
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView btDelete, productImage;
        View cardview;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            btDelete = itemView.findViewById(R.id.bt_delete);
            productImage = itemView.findViewById(R.id.image_view);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}