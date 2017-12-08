package tinkoff.fintech.cpstool.model.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmData extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String kpp;
    private String capital;
    private String branch_type;
    private String branch_count;
    private String source;
    private String qc;
    private String hid;
    private String type;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private String okved_type;
    @SerializedName("address")
    private Address address;

    public String getKpp(){
        return kpp;
    }

    public void setKpp(String kpp){
        this.kpp = kpp;
    }

    public String getCapital(){
        return capital;
    }

    public void setCapital(String capital){
        this.capital = capital;
    }

    public String getBranch_type(){
        return branch_type;
    }

    public void setBranch_type(String branch_type){
        this.branch_type = branch_type;
    }

    public String getBranch_count(){
        return branch_count;
    }

    public void setBranch_count(String branch_count){
        this.branch_count = branch_count;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getHid(){
        return hid;
    }

    public void setHid(String hid){
        this.hid = hid;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getInn(){
        return inn;
    }

    public void setInn(String inn){
        this.inn = inn;
    }

    public String getOgrn(){
        return ogrn;
    }

    public void setOgrn(String ogrn){
        this.ogrn = ogrn;
    }

    public String getOkpo(){
        return okpo;
    }

    public void setOkpo(String okpo){
        this.okpo = okpo;
    }

    public String getOkved(){
        return okved;
    }

    public void setOkved(String okved){
        this.okved = okved;
    }

    public String getOkved_type(){
        return okved_type;
    }

    public void setOkved_type(String okved_type){
        this.okved_type = okved_type;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
    }
}