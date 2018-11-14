package kz.careme.android.modules.chat;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import kz.careme.android.model.Message;

public interface ChatView extends MvpView {
    void messageLoaded(List<Message> messages);

    void messageAdded();

    void addMessage(Message message);
}
