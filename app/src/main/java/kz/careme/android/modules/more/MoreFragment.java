package kz.careme.android.modules.more;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    public static final String TAG = "MoreFragment";

    @BindView(R.id.bottom_sheet_header_image)
    ImageView mHeaderImage;

    @BindView(R.id.bottom_sheet_text)
    TextView mHeaderText;
    public MoreFragment() {
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        mHeaderImage.setImageDrawable(getContext().getDrawable(R.drawable.ic_functionlight));
        mHeaderText.setText(R.string.more_functions);
        return view;
    }

}
