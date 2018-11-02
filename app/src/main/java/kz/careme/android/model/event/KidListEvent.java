package kz.careme.android.model.event;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kz.careme.android.model.Kid;
import kz.careme.android.model.actions.ActionKidList;

public class KidListEvent {

    private ActionKidList actionKidList;

    private static TypeAdapter<Double> DoubleTypeAdapter = new TypeAdapter<Double>() {

        @Override
        public void write(com.google.gson.stream.JsonWriter out, Double value) throws IOException {
            out.value(value);
        }

        @Override
        public Double read(com.google.gson.stream.JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Double.parseDouble(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

    };
    public KidListEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int i = 0;
        List<Kid> kids = new ArrayList<>();

        while (jsonObject.has(String.valueOf(i))) {
            Kid kid = new GsonBuilder().registerTypeAdapter(Double.class, DoubleTypeAdapter).create()
                    .fromJson(jsonObject.getJSONObject(String.valueOf(i)).toString(), Kid.class);
            kids.add(kid);
            i++;
        }
        Log.d("KidListEvent", "Deserialized!");
        actionKidList = new ActionKidList();
        actionKidList.setKids(kids);
    }

    public ActionKidList getActionKidList() {
        return actionKidList;
    }

    public void setActionKidList(ActionKidList actionKidList) {
        this.actionKidList = actionKidList;
    }
}
