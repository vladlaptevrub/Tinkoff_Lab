package tinkoff.fintech.cpstool.presenter.interfaces;

import java.util.List;

import tinkoff.fintech.cpstool.model.history.Party;

public interface IRequests {
    void setData(String title);
    List<Party> getData();
    Party findParty(String value);
    void setFavourite(String value, boolean b);
    void deleteItem(String Value);
    void clearHistoryData();

    //void saveCache(String title, String address, String inn);
    //void saveQuery(String queryFromUser, RealmList<Result> resultsRealm);
    //Result saveResult(String result);
    //RealmResults<Query> getQueries(String queryFromUser);
    //RealmResults<Cache> getCache(String suggestion);
}
