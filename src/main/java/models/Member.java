package src.main.java.models;

public class Member {
    private String firstName;
    private String lastName;
    private String gender;
    private int membershipNumber;

    public Member(String firstName, String lastName, String gender, int membershipNumber) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(int membershipNumber) {
        this.membershipNumber = membershipNumber;
    }
}
