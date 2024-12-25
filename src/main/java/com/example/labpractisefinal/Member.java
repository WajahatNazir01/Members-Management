package com.example.labpractisefinal;

public class Member {

    private String name;
    private String gender;
   private String membershiptype;
   private String dob;

    public Member(String name, String gender, String membershiptype, String dob) {
        this.name = name;
        this.gender = gender;
        this.membershiptype = membershiptype;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMembershiptype() {
        return membershiptype;
    }

    public void setMembershiptype(String membershiptype) {
        this.membershiptype = membershiptype;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
//        return "Member{" +
//                "name='" + name + '\'' +
//                ", gender='" + gender + '\'' +
//                ", membershiptype='" + membershiptype + '\'' +
//                ", dob='" + dob + '\'' +
//                '}';
        return  "NAME: "+name+"| GENDER: "+gender+"| MEMBERSHIPTYPE: "+membershiptype+"| DOB: "+dob;
    }
}
