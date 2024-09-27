package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class PartySongId implements Serializable {
    private Integer party_id;
    private Integer song_id;
}
