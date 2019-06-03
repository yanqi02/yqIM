package com.yq.yqim.model.bean;

public class MsgEntity {
    private String content;
    private String friend;
    private int type;
    private String username;

    public MsgEntity( String friend, String username,String content, int type) {
        this.content = content;
        this.friend = friend;
        this.type = type;
        this.username = username;
    }

    public MsgEntity() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MsgEntity(String friend, String username) {
        this.friend = friend;
        this.username = username;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "content='" + content + '\'' +
                ", friend='" + friend + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}
