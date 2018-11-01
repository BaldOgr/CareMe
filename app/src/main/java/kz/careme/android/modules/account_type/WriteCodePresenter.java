package kz.careme.android.modules.account_type;

import com.arellomobile.mvp.InjectViewState;
import com.squareup.otto.Subscribe;

import kz.careme.android.model.actions.ActionGenerateCode;
import kz.careme.android.model.actions.ActionActivateCode;
import kz.careme.android.model.event.CodeActivatedEvent;
import kz.careme.android.model.event.CodeGeneratedEvent;
import kz.careme.android.modules.BasePresenter;

@InjectViewState
public class WriteCodePresenter extends BasePresenter<WriteCodeView> {

    @Subscribe
    public void onCodeGenerated(CodeGeneratedEvent codeGeneratedEvent) {
        getViewState().setCode(codeGeneratedEvent.getGenerateCode().getCode());
        getViewState().dismissDialog();
    }

    @Subscribe
    public void onCodeActivated(CodeActivatedEvent codeActivatedEvent) {
        getViewState().dismissDialog();
        if (codeActivatedEvent.getActionActivateCode().getError() != null && !codeActivatedEvent.getActionActivateCode().getError().isEmpty()) {
            getViewState().showError(codeActivatedEvent.getActionActivateCode().getError());
            return;
        }
        getViewState().onCodeActivated(codeActivatedEvent.getActionActivateCode().getParentId(),
                codeActivatedEvent.getActionActivateCode().getChildId());
    }

    public void getParentCode(int kidId) {
        ActionGenerateCode actionGenerateCode = new ActionGenerateCode();
        actionGenerateCode.setSid(getProfiler().getAccount().getSid());
        actionGenerateCode.setKidId(kidId);
        getCallService().call(actionGenerateCode.toString());
    }

    public void activateCode(String code) {
        ActionActivateCode actionActivateCode = new ActionActivateCode();
        actionActivateCode.setCode(Integer.parseInt(code));
        getCallService().call(actionActivateCode.toString());
    }
}
