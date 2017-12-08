package tinkoff.fintech.cpstool.model.history;

import io.realm.RealmObject;

public class Cache extends RealmObject {

    private String title;
    private String inn;
    private String address;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setInn(String inn){
        this.inn = inn;
    }

    public String getInn(){
        return inn;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }
}
