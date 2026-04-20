package main.java.com.flc.booking;

public record Member(String memberId, String name, String email, String phoneNumber) {

    @Override
    public String toString() {
        return memberId + " - " + name;
    }
}
