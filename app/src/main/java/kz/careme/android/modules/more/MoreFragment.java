package kz.careme.android.modules.more;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.careme.android.CareMeApp;
import kz.careme.android.R;
import kz.careme.android.model.Const;
import kz.careme.android.model.Kid;
import kz.careme.android.modules.more.places.PlacesFragment;
import kz.careme.android.modules.parent_main.ChangeBehaviorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = "MoreFragment";

    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;

    @BindView(R.id.root)
    LinearLayout mLinearLayout;

    @BindView(R.id.bottom_sheet_header)
    View mHeader;

    private ArrayAdapter<Kid> kidArrayAdapter;

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
        mHeaderImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_functionlight));
        mHeaderText.setText(R.string.more_functions);
        kidArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                ((CareMeApp) getContext().getApplicationContext()).getCareMeComponent().getProfiler().getKids());
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return view;
    }

    @OnClick(R.id.button_send_signal)
    public void onSendSignalClick() {
        new AlertDialog.Builder(getContext())
                .setAdapter(kidArrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (kidArrayAdapter.getItem(which).getSessionId() == null || kidArrayAdapter.getItem(which).getSessionId().isEmpty()) {
                            Toast.makeText(getContext(), R.string.error_kid_session_empty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(new Intent(getContext(), PullABellActivity.class)
                                .putExtra(Const.KID_SESSION_ID, kidArrayAdapter.getItem(which).getSessionId()));
                    }
                })
                .show();
    }

    @OnClick(R.id.button_microphone)
    public void onSoundAroundPhoneClick() {
        new AlertDialog.Builder(getContext())
                .setAdapter(kidArrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), SoundAroundPhoneActivity.class)
                                .putExtra(Const.KID_ID, kidArrayAdapter.getItem(which).getId()));
                    }
                })
                .show();
    }

//    @OnClick(R.id.button_a_to_b)
//    public void onAToBClick() {
//        Toast.makeText(getContext(), "В разработке", Toast.LENGTH_SHORT).show();
//    }

//    @OnClick(R.id.button_chat)
//    public void onChatButtonClick() {
//        new AlertDialog.Builder(getContext())
//                .setAdapter(kidArrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(getContext(), ChatActivity.class)
//                                .putExtra(Const.KID, new Gson().toJson(kidArrayAdapter.getItem(which))));
//                    }
//                })
//                .show();
//    }

    @OnClick(R.id.button_places)
    public void onPlacesButtonClick() {
        if (getActivity() == null) return;
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(PlacesFragment.TAG);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = PlacesFragment.newInstance(mChangeBehaviorListener);
            transaction.add(R.id.bottom_sheet_behavior_content, fragment, PlacesFragment.TAG);
        }
        transaction.show(fragment)
                .addToBackStack(null)
                .hide(this)
                .commit();
    }

    @OnClick(R.id.button_support)
    public void onSupportButtonClick() {
        startActivity(new Intent(getContext(), SupportActivity.class));
    }

    @Override
    public void onGlobalLayout() {
        if (isVisible()) {
            mChangeBehaviorListener.changeBehaviorPeekSize(mLinearLayout.getHeight());
        }
    }

}
