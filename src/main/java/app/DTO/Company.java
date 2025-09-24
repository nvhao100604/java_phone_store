package app.DTO;

public class Company {
    private int companyId;
    private String companyName;
    private int companyStatus;

    public Company() {}

    public Company(int companyId, String companyName, int companyStatus) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyStatus = companyStatus;
    }

    public Company(String companyName) {
        this.companyName = companyName;
    }

    // Getters and Setters
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(int companyStatus) {
        this.companyStatus = companyStatus;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyStatus='" + companyStatus + '\'' +
                '}';

    }
}