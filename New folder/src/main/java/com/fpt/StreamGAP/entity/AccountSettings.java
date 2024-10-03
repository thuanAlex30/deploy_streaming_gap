package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Account_Settings")
@Data
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String privacy = "public";
    private Boolean email_notifications = true;
    private Integer volume_level = 50;
    private Integer sleep_timer;
}
