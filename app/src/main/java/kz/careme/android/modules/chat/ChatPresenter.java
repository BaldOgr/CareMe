package kz.careme.android.modules.chat;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.Kid;
import kz.careme.android.model.actions.ActionGetMessage;
import kz.careme.android.model.actions.ActionSendMessage;
import kz.careme.android.model.event.MessageAddedEvent;
import kz.careme.android.model.event.MessageLoadedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class ChatPresenter extends BasePresenter<ChatView> {

    @Subscribe
    public void onMessagesLoaded(MessageLoadedEvent event) {
        getViewState().messageLoaded(event.getMessages());
    }

    @Subscribe
    public void onMessageAdded(MessageAddedEvent event) {
        if (event.getMessage().equals("Added")) {
            getViewState().messageAdded();
        }
    }

    public void getMessage(Kid kid) {
        ActionGetMessage getMessage = new ActionGetMessage();
        getMessage.setKidSessionId(kid.getSessionId());
        getMessage.setSessionId(getProfiler().getAccount().getSid());
        getCallService().call(getMessage);
    }

    public void getMessage(String parentSessionId) {
        ActionGetMessage getMessage = new ActionGetMessage();
        getMessage.setSessionId(parentSessionId);
        getMessage.setKidSessionId(getProfiler().getAccount().getSid());
        getCallService().call(getMessage);
    }

    public void sendMessage(Kid kid, String message) {
        ActionSendMessage sendMessage = new ActionSendMessage();
        sendMessage.setSessionId(getProfiler().getAccount().getSid());
        sendMessage.setKidSessionId(kid.getSessionId());
        sendMessage.setMessage(message);
        getCallService().call(sendMessage);
    }
}
