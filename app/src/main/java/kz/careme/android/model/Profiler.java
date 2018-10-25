package kz.careme.android.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Profiler {
    private Account account;
    private List<Place> places;

    @Inject
    public Profiler() {
        places = new ArrayList<>();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places.addAll(places);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
