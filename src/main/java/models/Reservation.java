package src.main.java.models;

public class Reservation {
    private long isbn;
    private int memberId;
    private String borrowingDate;
    private boolean hasBeenReturned;

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
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
