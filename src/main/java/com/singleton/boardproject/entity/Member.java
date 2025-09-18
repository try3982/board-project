package com.singleton.boardproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;


    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member create(String email, String password) {
        return new Member(email, password);
    }

}
