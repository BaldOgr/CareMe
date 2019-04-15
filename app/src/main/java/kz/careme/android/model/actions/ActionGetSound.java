package kz.careme.android.model.actions;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.careme.android.model.Message;

public class ActionGetSound extends BaseAction {
    public static final String ACTION = "get_sound";

    private List<Record> recordList;

    @SerializedName("session_id")
    private String sid;

    @SerializedName("kid_id")
    private long kidId;


    public ActionGetSound() {
        setAction(ACTION);
    }

    public ActionGetSound(String json) throws JSONException {
        this();
        JSONObject jsonObject = new JSONObject(json);
        int i = 0;
        recordList = new ArrayList<>();
        while (jsonObject.has(String.valueOf(i))) {
            Record message = new Gson().fromJson(jsonObject.getJSONObject(String.valueOf(i)).toString(), Record.class);
            recordList.add(message);
            i++;
        }
        Log.d("MessageLoadedEvent", "Deserialized!");
    }

    public ActionGetSound(String sid, long kidId) {
        this();
        this.sid = sid;
        this.kidId = kidId;
    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getKidId() {
        return kidId;
    }

    public void setKidId(long kidId) {
        this.kidId = kidId;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public static class Record {

        @SerializedName("id")
        private long id;

        @SerializedName("date")
        private String date;

        @SerializedName("parent_id")
        private long parentId;

        @SerializedName("file")
        private String file;

        @SerializedName("session_id")
        private String sid;

        @SerializedName("kid_id")
        private long kidId;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public long getKidId() {
            return kidId;
        }

        public void setKidId(long kidId) {
            this.kidId = kidId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

    }
}
