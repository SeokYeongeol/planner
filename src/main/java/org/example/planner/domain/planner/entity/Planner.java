package org.example.planner.domain.planner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.planner.domain.user.entity.User;
import org.example.planner.entity.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "planner")
public class Planner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Planner(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
