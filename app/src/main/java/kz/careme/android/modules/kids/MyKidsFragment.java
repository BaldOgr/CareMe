package kz.careme.android.modules.kids;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.yandex.mapkit.geometry.Point;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.login.register.RegisterActivity;
import kz.careme.android.modules.parent_main.ChangeBehaviorListener;
import kz.careme.android.modules.parent_main.MapActivityView;
import kz.careme.android.modules.child_info.ChildInfoActivity;

public class MyKidsFragment extends MvpAppCompatFragment implements ViewTreeObserver.OnGlobalLayoutListener, MyKidsView {
    public static final String TAG = "MyKidsFragment";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.header)
    View mHeaderView;

    private KidsAdapter adapter;
    private ChangeBehaviorListener mChangeBehaviorListener;
    private MapActivityView mapActivityView;
    private boolean loaded = false;

    @InjectPresenter
    MyKidsPresenter presenter;

    public MyKidsFragment() {
    }

    public static MyKidsFragment newInstance(ChangeBehaviorListener mBottomSheetBehavior, MapActivityView mapActivityView) {
        MyKidsFragment fragment = new MyKidsFragment();
        fragment.mChangeBehaviorListener = mBottomSheetBehavior;
        fragment.mapActivityView = mapActivityView;
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
        adapter.setOnAddChildClick(new OnAddChildClick() {
            @Override
            public void onClick() {
                startActivity(new Intent(getContext(), RegisterActivity.class)
                        .putExtra(Const.ACTION, Const.ACTION_REGISTER_KID)
                        .putExtra(Const.ACCOUNT_TYPE, Const.TYPE_CHILD));
            }
        });
        mRecyclerView.setAdapter(adapter);
        adapter.setOnKidClick(new OnKidClick() {
            @Override
            public void onClick(Kid kid) {
                startActivity(new Intent(getContext(), ChildInfoActivity.class).putExtra(Const.KID, new Gson().toJson(kid)));
            }
        });
        presenter.getKids();
        return view;
    }


    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            mChangeBehaviorListener.changeBehaviorPeekSize(mHeaderView.getHeight() + mRecyclerView.getChildAt(0).getHeight());
        }
    }

    @UiThread
    @Override
    public void onDataLoaded(final List<Kid> kids) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loaded = true;
                    adapter.setKidsList(kids);
                    adapter.notifyDataSetChanged();
                }
            });
            for (Kid kid : kids) {
                if (kid.getLatitude() != null)
                    mapActivityView.setMarker(new Point(kid.getLatitude(), kid.getLongitude()), kid.getAvatar());
            }
        }
    }

    @OnClick(R.id.reload)
    public void onReloadClick(View v) {
        loaded = false;
        presenter.getKids();
        showLoadingAnimation(v);
    }

    private void showLoadingAnimation(final View v) {
//        v.animate()
//                .rotation(360)
//                .setDuration(1000)
//                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        if (!loaded && !animation.isRunning())
//                            v.animate()
//                                    .rotation(360)
//                                    .setDuration(1000)
//                            .setUpdateListener(this)
//                            .start();
//                    }
//                })
//                .start();
    }
}
