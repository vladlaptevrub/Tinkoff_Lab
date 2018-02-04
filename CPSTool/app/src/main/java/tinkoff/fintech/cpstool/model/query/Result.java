package tinkoff.fintech.cpstool.model.query;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Result extends RealmObject {

    @Index
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}