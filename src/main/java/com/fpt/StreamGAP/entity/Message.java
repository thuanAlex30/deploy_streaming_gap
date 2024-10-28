package com.fpt.StreamGAP.entity;

import com.fpt.StreamGAP.Status.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "messages") // Tên bảng trong cơ sở dữ liệu, có thể tùy chỉnh nếu cần
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Khóa chính

    private String senderName;

    private String receiverName;

    private String message;

    private String media;

    private Status status;

    private String mediaType;
}
