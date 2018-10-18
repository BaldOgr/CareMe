package kz.careme.android.modules.kids;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;

public class KidsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_HOLDER_CHILD = 0;
    public static final int VIEW_HOLDER_ADD = 1;
    private List<Kids> kidsList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == VIEW_HOLDER_ADD) {
            viewHolder = new AddChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_child_item, viewGroup, false));
        } else {
            viewHolder = new ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item, viewGroup, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ChildHolder) {
            ((ChildHolder) viewHolder).childName.setText(kidsList.get(i).getName());
            ((ChildHolder) viewHolder).lastDataUpdate.setText(kidsList.get(i).getImage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return kidsList == null || position >= kidsList.size() || kidsList.get(position) == null ? VIEW_HOLDER_ADD : VIEW_HOLDER_CHILD;
    }

    @Override
    public int getItemCount() {
        return kidsList == null ? 1 : kidsList.size() + 1;
    }

    public void setKidsList(List<Kids> kidsList) {
        this.kidsList = kidsList;
    }

    class ChildHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.child_name)
        TextView childName;

        @BindView(R.id.last_data_update)
        TextView lastDataUpdate;

        @BindView(R.id.battery)
        TextView battery;

        ChildHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AddChildHolder extends RecyclerView.ViewHolder {

        AddChildHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
