package src.main.java.models;

import src.main.java.enums.Gender;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String firstName;
    private String lastName;
    private Gender gender;
    private int membershipNumber;

    private List<Reservation> reservations;

    public Member(String firstName, String lastName, Gender gender, int membershipNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.membershipNumber = membershipNumber;
        this.reservations = new ArrayList<>();
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
