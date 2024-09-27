package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Table(name = "Party_Mode")
@Data
public class PartyMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer party_id;

    private String party_name;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    private Date created_at;
}
