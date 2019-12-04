package com.cotrav.cotravspoc.local_database.local_model.taxi;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "taxi_passengers")
public class TaxiPassenger
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_email")
    @Expose
    private String employeeEmail;
    @SerializedName("employee_contact")
    @Expose
    private String employeeContact;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("core_employee_id")
    @Expose
    private String coreEmployeeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCoreEmployeeId() {
        return coreEmployeeId;
    }

    public void setCoreEmployeeId(String coreEmployeeId) {
        this.coreEmployeeId = coreEmployeeId;
    }
}
