package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.User;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String massage;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private String email;
    private String avatar_url;
    private String login_provider;
    private String provider_id;
    private String subscription_type;
    private Date created_at;
    private Date updated_at;
    private String role;
    private String password;
    private User user;
    private List<User> userList;

}