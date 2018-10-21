package kz.careme.android.modules.more;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.BaseFragment;
import kz.careme.android.R;
import kz.careme.android.modules.ChangeBehaviorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = "MoreFragment";

    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;

    @BindView(R.id.buttons_root)
    LinearLayout mLinearLayout;

    private ChangeBehaviorListener mChangeBehaviorListener;

    public MoreFragment() {
    }

    public static MoreFragment newInstance(ChangeBehaviorListener mBottomSheetBehavior) {
        MoreFragment fragment = new MoreFragment();
        fragment.mChangeBehaviorListener = mBottomSheetBehavior;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_functionlight));
        mHeaderText.setText(R.string.more_functions);
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return view;
    }

    @OnClick(R.id.button_send_signal)
    public void onSendSignalClick() {
        startActivity(new Intent(getContext(), PullABellActivity.class));
    }


    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            mChangeBehaviorListener.changeBehaviorPeekSize(getView().getHeight());
        }
    }

}
