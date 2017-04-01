package nimoniks.handyman.webservice.webServiceResponseObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("state")
@Expose
private String state;
@SerializedName("email")
@Expose
private String email;
@SerializedName("type")
@Expose
private String type;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("id")
@Expose
private Integer id;

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

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

}