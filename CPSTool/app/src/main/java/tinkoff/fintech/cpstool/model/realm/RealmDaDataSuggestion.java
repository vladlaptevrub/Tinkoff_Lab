package tinkoff.fintech.cpstool.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmDaDataSuggestion extends RealmObject {
    @PrimaryKey
    private String uuid;

    private RealmList<RealmDaDataAnswer> suggestions;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RealmList<RealmDaDataAnswer> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(RealmList<RealmDaDataAnswer> suggestions) {
        this.suggestions = suggestions;
    }
}