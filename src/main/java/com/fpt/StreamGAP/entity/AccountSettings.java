package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Account_Settings")
@Data
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer account_settings_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    private String privacy = "public";
    private Boolean email_notifications = true;
    private Integer volume_level = 50;
    private Integer sleep_timer;

    public void setVolume_level(Integer volume_level) {
        if (volume_level != null && volume_level > 100) {
            this.volume_level = 100;
        } else {
            this.volume_level = volume_level;
        }
    }
}