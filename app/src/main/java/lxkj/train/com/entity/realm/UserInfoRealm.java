package lxkj.train.com.entity.realm;

import io.realm.RealmObject;

/**
 * Created by dell on 2018/6/14.
 */

public class UserInfoRealm extends RealmObject {
    private String name;  //姓名
    private String number; //工号
    private String bureau;//局
    private String segment;//段
    private String plant;//车间
    private String motorcade;//车队
    private String SteeringGroup;//指导组
    private String MentoringRelationship; //师徒关系
    private String driverLevel;//司机等级
    private String remarks;//备注
    private String wageBracket;//工资等级
    private String politicsStatus;//政治面貌
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getMotorcade() {
        return motorcade;
    }

    public void setMotorcade(String motorcade) {
        this.motorcade = motorcade;
    }

    public String getSteeringGroup() {
        return SteeringGroup;
    }

    public void setSteeringGroup(String steeringGroup) {
        SteeringGroup = steeringGroup;
    }

    public String getMentoringRelationship() {
        return MentoringRelationship;
    }

    public void setMentoringRelationship(String mentoringRelationship) {
        MentoringRelationship = mentoringRelationship;
    }

    public String getDriverLevel() {
        return driverLevel;
    }

    public void setDriverLevel(String driverLevel) {
        this.driverLevel = driverLevel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWageBracket() {
        return wageBracket;
    }

    public void setWageBracket(String wageBracket) {
        this.wageBracket = wageBracket;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }



}
