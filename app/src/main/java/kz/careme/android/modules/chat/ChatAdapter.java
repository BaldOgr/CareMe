package kz.careme.android.modules.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Message;

public class ChatAdapter extends RecyclerView.Adapter {
    private List<Message> messages = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == ((CareMeApp) viewGroup.getContext().getApplicationContext()).getCareMeComponent().getProfiler().getAccount().getRole()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_from_me, viewGroup, false);
            viewHolder = new FromMe(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_from_another, viewGroup, false);
            viewHolder = new FromAnother(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message message = messages.get(i);
        if (viewHolder instanceof FromMe) {
            ((FromMe) viewHolder).textView.setText(message.getMessage());
            ((FromMe) viewHolder).loading.setVisibility(message.isLoading() ? View.VISIBLE : View.GONE);
        } else if (viewHolder instanceof FromAnother) {
            ((FromAnother) viewHolder).textView.setText(message.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    public void setMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }

    public List<Message> getMessages() {
        return messages;
    }

    class FromMe extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView textView;

        @BindView(R.id.loading)
        ImageView loading;

        public FromMe(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FromAnother extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView textView;

        public FromAnother(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
