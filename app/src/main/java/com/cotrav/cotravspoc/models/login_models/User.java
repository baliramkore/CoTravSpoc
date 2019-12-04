package com.cotrav.cotravspoc.models.login_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("corporate_id")
        @Expose
        private Integer corporateId;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("subgroup_id")
        @Expose
        private Integer subgroupId;
        @SerializedName("user_cid")
        @Expose
        private Object userCid;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_contact")
        @Expose
        private String userContact;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("old_password")
        @Expose
        private Object oldPassword;
        @SerializedName("profile_image")
        @Expose
        private Object profileImage;
        @SerializedName("budget")
        @Expose
        private Object budget;
        @SerializedName("expense")
        @Expose
        private Double expense;
        @SerializedName("is_radio")
        @Expose
        private Integer isRadio;
        @SerializedName("is_local")
        @Expose
        private Integer isLocal;
        @SerializedName("is_outstation")
        @Expose
        private Integer isOutstation;
        @SerializedName("is_bus")
        @Expose
        private Integer isBus;
        @SerializedName("is_train")
        @Expose
        private Integer isTrain;
        @SerializedName("is_hotel")
        @Expose
        private Integer isHotel;
        @SerializedName("is_meal")
        @Expose
        private Integer isMeal;
        @SerializedName("is_flight")
        @Expose
        private Integer isFlight;
        @SerializedName("is_water_bottles")
        @Expose
        private Integer isWaterBottles;
        @SerializedName("is_reverse_logistics")
        @Expose
        private Integer isReverseLogistics;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("has_single_employee")
        @Expose
        private Integer hasSingleEmployee;
        @SerializedName("fcm_regid")
        @Expose
        private Object fcmRegid;
        @SerializedName("last_login")
        @Expose
        private String lastLogin;
        @SerializedName("is_deleted")
        @Expose
        private Integer isDeleted;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("modified")
        @Expose
        private String modified;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Integer getCorporateId() {
            return corporateId;
        }

        public void setCorporateId(Integer corporateId) {
            this.corporateId = corporateId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getSubgroupId() {
            return subgroupId;
        }

        public void setSubgroupId(Integer subgroupId) {
            this.subgroupId = subgroupId;
        }

        public Object getUserCid() {
            return userCid;
        }

        public void setUserCid(Object userCid) {
            this.userCid = userCid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserContact() {
            return userContact;
        }

        public void setUserContact(String userContact) {
            this.userContact = userContact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(Object oldPassword) {
            this.oldPassword = oldPassword;
        }

        public Object getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(Object profileImage) {
            this.profileImage = profileImage;
        }

        public Object getBudget() {
            return budget;
        }

        public void setBudget(Object budget) {
            this.budget = budget;
        }

        public Double getExpense() {
            return expense;
        }

        public void setExpense(Double expense) {
            this.expense = expense;
        }

        public Integer getIsRadio() {
            return isRadio;
        }

        public void setIsRadio(Integer isRadio) {
            this.isRadio = isRadio;
        }

        public Integer getIsLocal() {
            return isLocal;
        }

        public void setIsLocal(Integer isLocal) {
            this.isLocal = isLocal;
        }

        public Integer getIsOutstation() {
            return isOutstation;
        }

        public void setIsOutstation(Integer isOutstation) {
            this.isOutstation = isOutstation;
        }

        public Integer getIsBus() {
            return isBus;
        }

        public void setIsBus(Integer isBus) {
            this.isBus = isBus;
        }

        public Integer getIsTrain() {
            return isTrain;
        }

        public void setIsTrain(Integer isTrain) {
            this.isTrain = isTrain;
        }

        public Integer getIsHotel() {
            return isHotel;
        }

        public void setIsHotel(Integer isHotel) {
            this.isHotel = isHotel;
        }

        public Integer getIsMeal() {
            return isMeal;
        }

        public void setIsMeal(Integer isMeal) {
            this.isMeal = isMeal;
        }

        public Integer getIsFlight() {
            return isFlight;
        }

        public void setIsFlight(Integer isFlight) {
            this.isFlight = isFlight;
        }

        public Integer getIsWaterBottles() {
            return isWaterBottles;
        }

        public void setIsWaterBottles(Integer isWaterBottles) {
            this.isWaterBottles = isWaterBottles;
        }

        public Integer getIsReverseLogistics() {
            return isReverseLogistics;
        }

        public void setIsReverseLogistics(Integer isReverseLogistics) {
            this.isReverseLogistics = isReverseLogistics;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getHasSingleEmployee() {
            return hasSingleEmployee;
        }

        public void setHasSingleEmployee(Integer hasSingleEmployee) {
            this.hasSingleEmployee = hasSingleEmployee;
        }

        public Object getFcmRegid() {
            return fcmRegid;
        }

        public void setFcmRegid(Object fcmRegid) {
            this.fcmRegid = fcmRegid;
        }

        public String getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(String lastLogin) {
            this.lastLogin = lastLogin;
        }

        public Integer getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }}