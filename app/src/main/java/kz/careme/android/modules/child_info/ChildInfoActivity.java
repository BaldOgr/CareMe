package kz.careme.android.modules.child_info;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.careme.android.R;
import kz.careme.android.modules.BaseActivity;

public class ChildInfoActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mChildImage;

    @BindView(R.id.child_first_name)
    TextView mChildFirstName;

    @BindView(R.id.child_second_name)
    TextView mChildSecondName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);
        ButterKnife.bind(this);

    }
}
