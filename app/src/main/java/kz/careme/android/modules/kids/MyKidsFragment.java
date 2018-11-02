package kz.careme.android.modules.kids;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.ChangeBehaviorListener;

public class MyKidsFragment extends MvpAppCompatFragment implements ViewTreeObserver.OnGlobalLayoutListener, MyKidsView {
    public static final String TAG = "MyKidsFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.header)
    View mHeaderView;

    private KidsAdapter adapter;
    private ChangeBehaviorListener mChangeBehaviorListener;

    @InjectPresenter
    MyKidsPresenter presenter;

    public MyKidsFragment() {
    }

    public static MyKidsFragment newInstance(ChangeBehaviorListener mBottomSheetBehavior) {
        MyKidsFragment fragment = new MyKidsFragment();
        fragment.mChangeBehaviorListener = mBottomSheetBehavior;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_kids, container, false);
        ButterKnife.bind(this, view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new KidsAdapter();
//        adapter.setKidsList(getKids());
        mRecyclerView.setAdapter(adapter);
        presenter.getKids();
        return view;
    }

    private List<Kids> getKids() {
        ArrayList<Kids> kids = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Kids kids1 = new Kids();
            kids1.setName("Name " + i);
            kids1.setBattery("" + i);
            kids1.setImage("asdasdwqe" + i);
            kids.add(kids1);
        }
        return kids;
    }

    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            mChangeBehaviorListener.changeBehaviorPeekSize(mHeaderView.getHeight() + mRecyclerView.getChildAt(0).getHeight());
        }
    }

    @Override
    public void onDataLoaded(List<Kid> kids) {

    }
}
