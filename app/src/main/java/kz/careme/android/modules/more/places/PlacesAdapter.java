package kz.careme.android.modules.more.places;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Place;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    List<Place> places;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.address.setText(places.get(i).getAddress());
        viewHolder.name.setText(places.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.place_name)
        TextView name;
        @BindView(R.id.place_address)
        TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
