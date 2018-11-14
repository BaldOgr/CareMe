package kz.careme.android.modules.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.modules.parent_main.ChangeBehaviorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = "SettingsFragment";

    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;
    private ChangeBehaviorListener mChangeBehaviorListener;


    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(ChangeBehaviorListener mBottomSheetBehavior) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.mChangeBehaviorListener = mBottomSheetBehavior;
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_settingslight));
        mHeaderText.setText(R.string.settings);
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
