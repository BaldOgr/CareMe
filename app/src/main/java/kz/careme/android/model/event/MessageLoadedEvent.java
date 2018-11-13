package kz.careme.android.model.event;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.careme.android.model.Message;

public class MessageLoadedEvent {
    private List<Message> messages;

    public MessageLoadedEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int i = 0;
        messages = new ArrayList<>();
        while (jsonObject.has(String.valueOf(i))) {
            Message message = new Gson().fromJson(jsonObject.getJSONObject(String.valueOf(i)).toString(), Message.class);
            messages.add(message);
            i++;
        }
        Log.d("MessageLoadedEvent", "Deserialized!");
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
