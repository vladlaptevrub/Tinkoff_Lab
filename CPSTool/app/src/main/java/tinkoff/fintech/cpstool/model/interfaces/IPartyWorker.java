package tinkoff.fintech.cpstool.model.interfaces;

import java.util.List;

import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.history.Party;

public interface IPartyWorker {
    void saveData(String title);
    List<Party> getData();
    Party findParty(String value);
    void doFavourite(String value, boolean sign);
    void deleteItem(String value);
    void clearHistoryData();
}
