package com.abdul.admin.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class LinkedinUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String state;

    @Column(length = 2056)
    private String idToken;

    @Column(length = 2056)
    private String accessToken;

    @Column
    private String usedAuthCode;

    @Column
    private String tokenScope;

    @Column
    private String expiresIn;

    @Column
    private String tokenType;

    @Column
    private String picture;

    @OneToOne(mappedBy = "linkedinUser")
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
