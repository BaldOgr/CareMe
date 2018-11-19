package kz.careme.android.modules.chat;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import java.util.Collection;
import java.util.Collections;

import kz.careme.android.model.Const;
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
            Collections.reverse(event.getMessages());
            getViewState().messageLoaded(event.getMessages());
        }
    }

    @Subscribe
    public void onMessageAdded(MessageAddedEvent event) {
    }

    public void getMessage(int receiverId) {
        ActionGetMessage getMessage = new ActionGetMessage();
        if (getProfiler().getAccount().getAccountType() == Const.TYPE_PARENT) {
            getMessage.setParentId(getProfiler().getAccount().getId());
            getMessage.setKidId(receiverId);
        } else {
            getMessage.setKidId(getProfiler().getAccount().getId());
            getMessage.setParentId(receiverId);
        }
        getCallService().call(getMessage);
    }

    public void sendMessage(int receiverId, String message) {
        Message messageToAdapter = new Message();
        messageToAdapter.setLoading(true);
        messageToAdapter.setMessage(message);
        messageToAdapter.setType(getProfiler().getAccount().getRole());

        getViewState().addMessage(messageToAdapter);

        ActionSendMessage sendMessage = new ActionSendMessage();
        if (getProfiler().getAccount().getAccountType() == Const.TYPE_PARENT) {
            sendMessage.setKidId(receiverId);
            sendMessage.setParentId(getProfiler().getAccount().getId());
        } else {
            sendMessage.setKidId(getProfiler().getAccount().getId());
            sendMessage.setParentId(receiverId);
        }
        sendMessage.setType(getProfiler().getAccount().getRole());
        sendMessage.setMessage(message);
        sendMessage.setSid(getProfiler().getAccount().getSid());

        getCallService().call(sendMessage);
    }
}
