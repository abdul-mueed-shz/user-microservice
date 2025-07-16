package com.abdul.admin.adapter.out.persistence.entity;

import com.abdul.admin.adapter.out.persistence.entitylistener.UserEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, UserEntityListener.class})
@Table(name = "users")
@Entity
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Join table
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "google_user_id", referencedColumnName = "id")
    private GoogleUser googleUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "linkedin_user_id", referencedColumnName = "id")
    private LinkedinUser linkedinUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "twitter_user_id", referencedColumnName = "id")
    private TwitterUser twitterUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "github_user_id", referencedColumnName = "id")
    private GithubUser githubUser;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private Boolean isActive = Boolean.TRUE;

    @Column
    private Boolean isSystemLock = Boolean.FALSE;

    @Column
    private Boolean areCredentialsValid = Boolean.TRUE;

    @Column
    private Boolean emailVerified = Boolean.FALSE;

    /**
     * @return List of authorities based on the user's roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * Indicates whether the account is expired.
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.getIsActive();
    }

    /**
     * Indicates whether the account is locked.
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.getIsSystemLock();
    }

    /**
     * Indicates whether the credentials are expired.
     *
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getAreCredentialsValid();
    }
}