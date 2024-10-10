package com.fpt.StreamGAP.dto;

import com.fpt.StreamGAP.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class PartyModeDTO {
    private Integer party_Id;
    private String party_Name;
    private Integer host_Id;
    private Date created_at;
}
