package kz.careme.android.model;

import javax.inject.Inject;

public class Profiler {
    private int id;
    private String name;

    @Inject
    public Profiler() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
