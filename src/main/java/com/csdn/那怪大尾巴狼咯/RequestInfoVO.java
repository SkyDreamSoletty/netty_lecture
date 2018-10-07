package com.csdn.那怪大尾巴狼咯;

public class RequestInfoVO {

    private byte type;
    private int sequence;
    private String body;

    public RequestInfoVO() {
    }

    public RequestInfoVO(byte type, int sequence, String body) {
        this.type = type;
        this.sequence = sequence;
        this.body = body;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
