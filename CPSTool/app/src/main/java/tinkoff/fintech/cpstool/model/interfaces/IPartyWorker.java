package tinkoff.fintech.cpstool.model.interfaces;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.model.history.Cache;
import tinkoff.fintech.cpstool.model.realm.Query;
import tinkoff.fintech.cpstool.model.realm.Result;

public interface IPartyWorker {
    void saveData(String title);
    List<Party> getData();
    Party findParty(String value);
    void doFavourite(String value, boolean sign);
    void deleteItem(String value);
    void clearHistoryData();
    void saveCache(String title, String address, String inn);
    void saveQuery(String queryFromUser, RealmList<Result> resultsRealm);
    Result saveResult(String result);
    RealmResults<Query> getQueries(String queryFromUser);
    RealmResults<Cache> getCache(String title);
}
