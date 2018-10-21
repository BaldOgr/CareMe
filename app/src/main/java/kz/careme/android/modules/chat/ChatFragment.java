package kz.careme.android.modules.chat;


import android.os.Bundle;
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
import kz.careme.android.modules.ChangeBehaviorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = "ChatFragment";

    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;
    private ChangeBehaviorListener mChangeBehaviorListener;

    public ChatFragment() {
    }

    public static ChatFragment newInstance(ChangeBehaviorListener mBottomSheetBehavior) {
        ChatFragment fragment = new ChatFragment();
        fragment.mChangeBehaviorListener = mBottomSheetBehavior;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_functionlight));
        mHeaderText.setText("Chat");
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
