package com.example.siap;

public class setModel {

    public String name;
    public String url;

    public  String noreg;
    public String pangkatnm;
    public String satuan;
    public String pasal;
    public String noputusan;
    public String noeksekusi;
    public String tglarsip;
    public String norak;
    public String noarsip;




    public setModel(String name, String url, String noreg, String pangkatnm, String satuan, String pasal, String noputusan, String noeksekusi, String tglarsip,String norak, String noarsip) {
        this.name = name;
        this.url = url;
        this.noreg = noreg;
        this.pangkatnm = pangkatnm;
        this.satuan = satuan;
        this.pasal = pasal;
        this.noputusan = noputusan;
        this.noeksekusi = noeksekusi;
        this.tglarsip = tglarsip;
        this.norak = norak;
        this.noarsip = noarsip;
    }

    public String getNorak() {
        return norak;
    }

    public void setNorak(String norak) {
        this.norak = norak;
    }

    public String getNoarsip() {
        return noarsip;
    }

    public void setNoarsip(String noarsip) {
        this.noarsip = noarsip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public String getPangkatnm() {
        return pangkatnm;
    }

    public void setPangkatnm(String pangkatnm) {
        this.pangkatnm = pangkatnm;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getPasal() {
        return pasal;
    }

    public void setPasal(String pasal) {
        this.pasal = pasal;
    }

    public String getNoputusan() {
        return noputusan;
    }

    public void setNoputusan(String noputusan) {
        this.noputusan = noputusan;
    }

    public String getNoeksekusi() {
        return noeksekusi;
    }

    public void setNoeksekusi(String noeksekusi) {
        this.noeksekusi = noeksekusi;
    }

    public String getTglarsip() {
        return tglarsip;
    }

    public void setTglarsip(String tglarsip) {
        this.tglarsip = tglarsip;
    }

    //    public setModel(String name, String url) {
//        this.name = name;
//        this.url = url;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}
