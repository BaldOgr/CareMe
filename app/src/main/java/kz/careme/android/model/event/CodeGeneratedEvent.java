package kz.careme.android.model.event;

import kz.careme.android.model.actions.ActionGenerateCode;

public class CodeGeneratedEvent {
    private ActionGenerateCode generateCode;

    public CodeGeneratedEvent(ActionGenerateCode generateCode) {
        this.generateCode = generateCode;
    }

    public ActionGenerateCode getGenerateCode() {
        return generateCode;
    }

    public void setGenerateCode(ActionGenerateCode generateCode) {
        this.generateCode = generateCode;
    }
}
