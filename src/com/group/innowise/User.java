package com.group.innowise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static final int MAX_ARR_SIZE = 3;
    public static final Pattern EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern PHONE =
            Pattern.compile("^(375|80)(29|25|44|33)[' '](\\d{3})(\\d{2})(\\d{2})$", Pattern.CASE_INSENSITIVE);

    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<>(3);
    private String email;
    private List<String> phones = new ArrayList<>(3);

    public User(){
        setFirstName("");
        setLastName("");
    }

    public User(String firstName, String lastName, List<String> roles, String email, List<String> phones) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.email = email;
        this.phones = phones;
    }

    public User(String firstName, String lastName, String role, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        setRole(role);
        setEmail(email);
        setPhone(phone);
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL.matcher(email);
        return matcher.find();
    }

    public static boolean validatePhone(String phone) {
        Matcher matcher = PHONE.matcher(phone);
        return matcher.find();
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

    public List<String> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (validateEmail(email)) {
            this.email = email;
            return true;
        } else {
            email = "";
            return false;
        }
    }

    public boolean setRole(String role) {
        if (roles.size() <= MAX_ARR_SIZE) {
            roles.add(role);
            return true;
        } else {
            return false;
        }
    }
    public void setRoleFoXML(String role) {
            roles.add(role);
    }

    public String getRole(String role) {
        return role;
    }


    public List<String> getPhones() {
        return phones;
    }

    public String getPhone(String phone) {
        return phone;
    }

    public boolean setPhone(String phone) {
        if (validatePhone(phone) && phones.size() <= MAX_ARR_SIZE) {
            phones.add(phone);
            return true;
        } else {
            return false;
        }
    }

    public void setPhoneForXML(String phone) {
            phones.add(phone);
    }

    public void fillDefaultRole(){
        for (int i = roles.size(); i <3; i++){
            roles.add(" ");
        }
    }

    public void fillDefaultPhone(){
            for (int i = phones.size(); i <3; i++){
                phones.add(" ");
            }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getRoles(), user.getRoles()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPhones(), user.getPhones());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName(), getRoles(), getEmail(), getPhones());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                '}';
    }
}
