package org.example.planner.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.planner.entity.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    private User(Long id) { this.id = id; }

    public static User fromUserId(Long id) {
        return new User(id);
    }
}
