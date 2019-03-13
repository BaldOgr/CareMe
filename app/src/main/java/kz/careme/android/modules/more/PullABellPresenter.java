package kz.careme.android.modules.more;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class PullABellPresenter extends BasePresenter<PullABellView> {

    public PullABellPresenter(Context context) {
        super(context);
    }

    public void pullABell(String kidSessionId){
        ActionPullABell pullABell = new ActionPullABell();
        pullABell.setKidSessionId(kidSessionId);
        pullABell.setSessionId(getProfiler().getAccount().getSid());
        getCallService().call(pullABell);
    }
}
