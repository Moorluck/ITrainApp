package be.bxl.itrainapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.bxl.itrainapp.FavoritesFragment;
import be.bxl.itrainapp.R;
import be.bxl.itrainapp.models.Train;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {

    ArrayList<Train> trains;
    public static ListItemClickListener listItemClickListener;
    public static FavoriteItemClickListener favoriteItemClickListener;

    public TrainAdapter(ArrayList<Train> trains) {
        this.trains = trains;
    }

    public interface ListItemClickListener {
        void onListItemClick(String trainId);
    }

    public static void setListItemClickListener(ListItemClickListener listItemClickListener) {
        TrainAdapter.listItemClickListener = listItemClickListener;
    }

    public interface FavoriteItemClickListener {
        void onFavoriteItemClick(String trainId);
    }

    public static void setFavoriteItemClickListener(FavoriteItemClickListener favoriteItemClickListener) {
        TrainAdapter.favoriteItemClickListener = favoriteItemClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDestination, tvDeparture, tvRetard, tvQuai;
        ImageView btnFavorites;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvDestination = v.findViewById(R.id.tv_item_destination);
            tvDeparture = v.findViewById(R.id.tv_item_departure);
            tvRetard = v.findViewById(R.id.tv_item_late);
            tvQuai = v.findViewById(R.id.tv_item_quai);
            btnFavorites = v.findViewById(R.id.img_item_favorites);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View trainView = layoutInflater.inflate(R.layout.item_train, parent, false);

        return new ViewHolder(trainView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.ViewHolder holder, int position) {

        Train train = trains.get(position);

        holder.tvDestination.setText(train.getDestination());
        holder.tvDeparture.setText(train.getDepart());
        holder.tvRetard.setText(String.format("Retard : %s %s", String.valueOf(train.getRetard()), "'"));
        holder.tvQuai.setText(String.format("Quai : %s", String.valueOf(train.getRetard())));

        holder.btnFavorites.setOnClickListener(v -> {
            favoriteItemClickListener.onFavoriteItemClick(train.getId());
        });

    }

    @Override
    public int getItemCount() {
        return trains.size();
    }

}
