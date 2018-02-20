package com.applabs.mysampleapp.model;

public class Invites {
    String senderEmail;
    String receiverEmail;

    public String getInvitesKey() {
        return invitesKey;
    }

    public void setInvitesKey(String invitesKey) {
        this.invitesKey = invitesKey;
    }

    String invitesKey;

    public String getInviteMessage() {
        return inviteMessage;
    }

    public void setInviteMessage(String inviteMessage) {
        this.inviteMessage = inviteMessage;
    }

    String inviteMessage;
    boolean allow = false;


    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }
}
