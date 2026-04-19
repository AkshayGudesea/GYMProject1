package main.java.com.flc.booking;

public class Member {
    private final String memberId;
    private final String name;
    private final String email;
    private final String phoneNumber;

    public Member(String memberId, String name, String email, String phoneNumber) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return memberId + " - " + name;
    }
}
