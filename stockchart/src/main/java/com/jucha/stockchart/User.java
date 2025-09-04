package com.jucha.stockchart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;      // google, naver, kakao
    private String providerId;    // 소셜 고유 ID
    private String email;
    private String name;
    private String profileImage;
    private String role;          // USER, ADMIN
}
