package kz.careme.android.model.event;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.careme.android.model.Message;

public class MessageLoadedEvent {
    private List<Message> messages;

    @SerializedName("msg")
    private String message;

    public MessageLoadedEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int i = 0;
        messages = new ArrayList<>();
        while (jsonObject.has(String.valueOf(i))) {
            Message message = new Gson().fromJson(jsonObject.getJSONObject(String.valueOf(i)).toString(), Message.class);
            messages.add(message);
            i++;
        }
        if (jsonObject.has("msg")) {
            message = jsonObject.getString("msg");
        }
        Log.d("MessageLoadedEvent", "Deserialized!");
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
