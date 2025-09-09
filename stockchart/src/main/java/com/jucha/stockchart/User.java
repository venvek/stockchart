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
    private String username;      // ✅ name 대신 username 사용
    private String profileImage;  // 프로필 이미지
    private String role;          // USER, ADMIN
    private String subscription_plan;
}
