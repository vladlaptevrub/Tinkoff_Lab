package tinkoff.fintech.cpstool.presenter.requests;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.model.history.PartyWorker;
import tinkoff.fintech.cpstool.presenter.interfaces.IRequests;

public class Requests implements IRequests {
    private PartyWorker mModel;

    public Requests(){
        mModel = new PartyWorker();
    }

    @Override
    public void setData(String title) {
        mModel.saveData(title);
    }

    @Override
    public List<Party> getData() {
        return mModel.getData();
    }

    @Override
    public Party findParty(String value) {
        return mModel.findParty(value);
    }

    @Override
    public void setFavourite(String value, boolean b) {
        mModel.doFavourite(value, b);
    }

    @Override
    public void deleteItem(String value) {
        mModel.deleteItem(value);
    }

    @Override
    public void clearHistoryData() {
        mModel.clearHistoryData();
    }
}
