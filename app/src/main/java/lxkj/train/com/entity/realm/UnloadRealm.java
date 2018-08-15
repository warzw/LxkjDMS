package lxkj.train.com.entity.realm;

import io.realm.RealmObject;

/**
 * Created by dell on 2018/8/10.
 */

public class UnloadRealm extends RealmObject {
    private String number;
    private String FileName;
    private String creatTime;
    private String FileZise;
    private String staus;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getFileZise() {
        return FileZise;
    }

    public void setFileZise(String fileZise) {
        FileZise = fileZise;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

}
