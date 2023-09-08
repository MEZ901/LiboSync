package src.main.java.models;

public class Reservation {
    private String isbn;
    private int memberId;
    private String borrowingDate;
    private boolean hasBeenReturned;

    public Reservation(String isbn, int memberId, String borrowingDate, boolean hasBeenReturned) {
        this.isbn = isbn;
        this.memberId = memberId;
        this.borrowingDate = borrowingDate;
        this.hasBeenReturned = hasBeenReturned;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
