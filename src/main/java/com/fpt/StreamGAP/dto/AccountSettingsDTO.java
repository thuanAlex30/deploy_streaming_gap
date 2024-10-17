package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountSettingsDTO {
    private Integer account_settings_id;
    private Integer user_id;
    private String privacy;
    private Boolean email_notifications;
    private Integer volume_level;
    private Integer sleep_timer;
}