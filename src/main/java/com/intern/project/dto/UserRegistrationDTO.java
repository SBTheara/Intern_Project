package com.intern.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createAt;
}
