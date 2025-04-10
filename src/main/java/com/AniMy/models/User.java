package com.AniMy.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(
        unique = true,
        nullable = false
    )
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(
        unique = true,
        nullable = false
    )
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

}
