package com.java.tanghao;

public class Profile{
    private String address;
    private String affiliation;
    private String affiliation_zh;
    private String bio;
    private String edu;
    private String email;
    private String email_cr;
    private EmailsU[] emails_u;
    private String fax;
    private String homepage;
    private String note;
    private String phone;
    private String position;
    private String work;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setAffiliation_zh(String affiliation_zh) {
        this.affiliation_zh = affiliation_zh;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail_cr(String email_cr) {
        this.email_cr = email_cr;
    }

    public void setEmails_u(EmailsU[] emails_u) {
        this.emails_u = emails_u;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAddress() {
        return address;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getAffiliation_zh() {
        return affiliation_zh;
    }

    public String getBio() {
        return bio;
    }

    public String getEdu() {
        return edu;
    }

    public EmailsU[] getEmails_u() {
        return emails_u;
    }

    public String getEmail_cr() {
        return email_cr;
    }

    public String getFax() {
        return fax;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getNote() {
        return note;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getWork() {
        return work;
    }
}
