package com.cotrav.cotravspoc.models.show_taxi_model.show_taxi;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
(tableName = "movie", foreignKeys = @ForeignKey(entity = TaxiBooking.class,
        parentColumns = "id",
        childColumns = "directorId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("directorId")})

public class Passanger {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("core_employee_id")
    @Expose
    private String coreEmployeeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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
