package tinkoff.fintech.cpstool.model.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.interfaces.IPartyWorker;

public class PartyWorker implements IPartyWorker{
    private Realm mRealm;

    private final static String FALSE = "false";
    private final static String TRUE = "true";

    public PartyWorker(){
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void saveData(String title) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", title).findAll();
        if (parties.isEmpty()) {
            Cache data = findData(title);
            if (data != null) {
                doTransaction(data);
            } else {
                Log.i("Model", "Didn't find '" + title + "' in cache");
            }
        }
    }

    @Override
    public List<Party> getData() {
        return getSortedData();
    }

    private Cache findData(String value){

        RealmResults<Cache> data =
                mRealm.where(Cache.class).equalTo("title", value).findAll();

        if (!data.isEmpty()){
            return data.get(0);
        } else {
            return null;
        }
    }

    private void doTransaction(Cache data){
        mRealm.beginTransaction();
        Party newParty = mRealm.createObject(Party.class);
        newParty.setTitle(data.getTitle());
        newParty.setInn(data.getInn());
        newParty.setAddress(data.getAddress());
        newParty.setFavourite(FALSE);
        mRealm.commitTransaction();
    }

    @Override
    public Party findParty(String value) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        if (!parties.isEmpty()){
            return parties.get(0);
        } else {
            Log.i("Model", "Didn't find '" + value + "' in storage");
            return null;
        }
    }

    @Override
    public void doFavourite(String value, boolean sign) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        if (!parties.isEmpty()){
            mRealm.beginTransaction();
            if (sign) {
                parties.get(0).setFavourite(TRUE);
            } else {
                parties.get(0).setFavourite(FALSE);
            }
            mRealm.commitTransaction();
        }
    }

    @Override
    public void deleteItem(String value) {
        mRealm.beginTransaction();
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        parties.clear();
        mRealm.commitTransaction();
    }

    @Override
    public void clearHistoryData() {
        mRealm.beginTransaction();
        RealmResults<Party> parties = mRealm.where(Party.class).findAll();
        parties.clear();
        mRealm.commitTransaction();
    }

    private List<Party> getSortedData(){
        RealmResults<Party> partiesResults = mRealm.where(Party.class).findAll();
        List<Party> favouriteParties = new ArrayList<>();
        List<Party> simpleParties = new ArrayList<>();
        List<Party> sortedList = new ArrayList<>();

        for (int i = 0; i < partiesResults.size(); i++){
            if (partiesResults.get(i).getFavourite().equals(TRUE)){
                favouriteParties.add(partiesResults.get(i));
            } else {
                simpleParties.add(partiesResults.get(i));
            }
        }

        sortedList.addAll(favouriteParties);
        sortedList.addAll(simpleParties);

        return sortedList;
    }
}
