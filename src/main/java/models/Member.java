package src.main.java.models;

import src.main.java.enums.Gender;

public class Member {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int membershipNumber;

    public Member(String firstName, String lastName, Gender gender, int membershipNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.membershipNumber = membershipNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(int membershipNumber) {
        this.membershipNumber = membershipNumber;
    }
}
