package com.fpt.StreamGAP.dto;

import lombok.Data;

@Data
public class PartyModeDTO {
    private Integer partyId;
    private String partyName;
    private Integer hostId;
    private String hostName;
    private String createdAt;
}
