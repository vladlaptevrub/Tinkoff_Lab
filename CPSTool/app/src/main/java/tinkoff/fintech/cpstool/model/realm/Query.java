package tinkoff.fintech.cpstool.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Query extends RealmObject {

    @PrimaryKey
    private String id;

    @Index
    private String query;

    private RealmList<Result> result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public RealmList<Result> getResult() {
        return result;
    }

    public void setResult(RealmList<Result> result) {
        this.result = result;
    }
}