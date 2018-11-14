package kz.careme.android.modules.chat;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Kid;
import kz.careme.android.model.Message;
import kz.careme.android.model.actions.ActionGetMessage;
import kz.careme.android.model.actions.ActionSendMessage;
import kz.careme.android.model.event.MessageAddedEvent;
import kz.careme.android.model.event.MessageLoadedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class ChatPresenter extends BasePresenter<ChatView> {

    @Subscribe
    public void onMessagesLoaded(MessageLoadedEvent event) {
        if ("Added".equals(event.getMessage())) {
            getViewState().messageAdded();
        } else if (event.getMessages() != null) {
            getViewState().messageLoaded(event.getMessages());
        }
    }

    @Subscribe
    public void onMessageAdded(MessageAddedEvent event) {
    }

    public void getMessage(int receiverId) {
        ActionGetMessage getMessage = new ActionGetMessage();
        getMessage.setSenderId(getProfiler().getAccount().getId());
        getMessage.setReceiverId(receiverId);
        getCallService().call(getMessage);
    }

    public void sendMessage(int receiverId, String message) {
        Message messageToAdapter = new Message();
        messageToAdapter.setLoading(true);
        messageToAdapter.setMessage(message);
        messageToAdapter.setType(getProfiler().getAccount().getAccountType());

        getViewState().addMessage(messageToAdapter);

        ActionSendMessage sendMessage = new ActionSendMessage();
        sendMessage.setSenderId(getProfiler().getAccount().getId());
        sendMessage.setReceiverId(receiverId);
        sendMessage.setMessage(message);

        getCallService().call(sendMessage);
    }
}
