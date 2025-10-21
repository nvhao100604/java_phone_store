package app.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private int employeeId;
    private int accountId;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth; 
    private String userName;
    private String address;
    private BigDecimal salary;
    private int status;

    public Employee() {}

    public Employee(int employeeId, String fullName, String gender, Date dateOfBirth, String phoneNumber, String email, int status) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email; 
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public Employee(int accountId, String gender, Date dateOfBirth, String address) {
        this.accountId = accountId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth; 
        this.address = address;
    }

    public Employee(int accountId, String gender, Date dateOfBirth, String address, int status) {
        this.accountId = accountId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth; 
        this.address = address;
        this.status = status;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public Date getDateOfBirth () {
        return dateOfBirth;
    }

    public void setDateOfBirth (Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return gender;
    }
}