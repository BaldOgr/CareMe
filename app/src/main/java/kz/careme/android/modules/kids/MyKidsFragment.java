package kz.careme.android.modules.kids;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import kz.careme.android.R;
import kz.careme.android.modules.ChangeBehaviorListener;

public class MyKidsFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = "MyKidsFragment";

    private ChangeBehaviorListener mChangeBehaviorListener;

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
        final View view = inflater.inflate(R.layout.fragment_my_kids, container, false);
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return view;
    }

    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            mChangeBehaviorListener.changeBehaviorPeekSize(getView().getHeight());
        }
    }
}
