package jdisa.homesafety.Data_Form;

/**
 * Created by jrdis on 8/7/2017.
 */

public class ImageUpload {
    public String name;
    public String url;
    public String device;

    public ImageUpload() {
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }


}
