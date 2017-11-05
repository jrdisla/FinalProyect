package jdisa.homesafety.Data_Form;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by jrdis on 1/11/2017.
 */
@IgnoreExtraProperties
public  class Data {
    public Long co2;
    public String day;
    public Long hume;
    public Long pg;
    public Long temp;

    public Data() {
    }

    public Long getCo2() {
        return co2;
    }

    public void setCo2(Long co2) {
        this.co2 = co2;
    }

    public Long getHume() {
        return hume;
    }

    public void setHume(Long hume) {
        this.hume = hume;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getPg() {
        return pg;
    }

    public void setPg(Long pg) {
        this.pg = pg;
    }

    public Long getTemp() {
        return temp;
    }

    public void setTemp(Long temp) {
        this.temp = temp;
    }
}
