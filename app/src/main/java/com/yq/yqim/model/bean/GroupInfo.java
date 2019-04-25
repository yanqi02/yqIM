package com.yq.yqim.model.bean;

public class GroupInfo {
    private String groupName;//群名
    private String groupId;  //群id
    private String invatePersion; //邀请人

    public GroupInfo() {
    }

    public GroupInfo(String groupName, String groupId, String invatePersion) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.invatePersion = invatePersion;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInvatePersion() {
        return invatePersion;
    }

    public void setInvatePersion(String invatePersion) {
        this.invatePersion = invatePersion;
    }

    @Override
    public String toString() {
        return "GroupInfo{" + "groupName='" + groupName + '\'' + ", groupId='" + groupId + '\'' + ", invatePersion='" + invatePersion + '\'' + '}';
    }
}
