package io.ethertale.reasonanddominationspringdefenseproject.account.model;

import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.playerCharacter.model.Hero;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;
    @Column(name = "profile_picture", length = 1000)
    private String profilePicture;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile")
    private List<Hero> heroes;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<ForumPost> posts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<ForumPostContent> comments;
    @Column(nullable = false)
    private LocalDateTime createdOn;

}
