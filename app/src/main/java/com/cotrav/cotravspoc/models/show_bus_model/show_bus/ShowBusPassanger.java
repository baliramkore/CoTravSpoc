package com.cotrav.cotravspoc.models.show_bus_model.show_bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowBusPassanger {

        @SerializedName("id")
        @Expose
        private String id;
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
        private String age;
        @SerializedName("gender")
        @Expose
        private Object gender;
        @SerializedName("core_employee_id")
        @Expose
        private Object coreEmployeeId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getCoreEmployeeId() {
            return coreEmployeeId;
        }

        public void setCoreEmployeeId(Object coreEmployeeId) {
            this.coreEmployeeId = coreEmployeeId;
        }

    }