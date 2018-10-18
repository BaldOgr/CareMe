package kz.careme.android.modules.subscribe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;

public class SubscribeFragment extends Fragment {

    public static final String TAG = "SubscribeFragment";
    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;

    public SubscribeFragment() {
    }

    public static SubscribeFragment newInstance() {
        SubscribeFragment fragment = new SubscribeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_buylight));
        mHeaderText.setText(R.string.subscribe);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
