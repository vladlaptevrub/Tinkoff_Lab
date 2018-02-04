package tinkoff.fintech.cpstool.model.interfaces;

import java.util.List;

import tinkoff.fintech.cpstool.model.history.Party;

public interface IPartyWorker {
    void saveData(String title);
    List<Party> getData();
    Party findParty(String value);
    void doFavourite(String value, boolean sign);
    void deleteItem(String value);
    void clearHistoryData();

    //void saveCache(String title, String address, String inn);
    //void saveQuery(String queryFromUser, RealmList<Result> resultsRealm);
    //Result saveResult(String result);
    //RealmResults<Query> getQueries(String queryFromUser);
    //RealmResults<Cache> getCache(String title);
}
