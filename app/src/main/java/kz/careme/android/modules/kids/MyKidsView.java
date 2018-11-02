package kz.careme.android.modules.kids;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import kz.careme.android.model.Kid;

public interface MyKidsView extends MvpView {

    void onDataLoaded(List<Kid> kids);
}
