package src.main.java.models;

public class Reservation {
    private String isbn;
    private Member member;
    private String borrowingDate;
    private boolean hasBeenReturned;

    public Reservation(String isbn, Member member, String borrowingDate, boolean hasBeenReturned) {
        this.isbn = isbn;
        this.member = member;
        this.borrowingDate = borrowingDate;
        this.hasBeenReturned = hasBeenReturned;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public boolean isHasBeenReturned() {
        return hasBeenReturned;
    }

    public void setHasBeenReturned(boolean hasBeenReturned) {
        this.hasBeenReturned = hasBeenReturned;
    }
}
