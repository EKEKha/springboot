package com.example.jwtmember.entity;


import com.example.jwtmember.jwt.UserStatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uniqId;

    private String loginId;

    private String password;

    private String userName;

    private String cellNo;

    private String role;

//    @Enumerated(EnumType.STRING)
//    private UserStatusCode userStatusCode;

    private String userStatusCode;

    private String lockAt;

    private String lockCnt;

    private LocalDateTime lockLastDt;

}
