package project.musicwebsite.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.musicwebsite.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class UserPrincipal implements UserDetails {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal build(User user) {
        List<GrantedAuthority> authorities1 = convertAuthorities(user.getRole());
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities1
        );

    }

    public static List<GrantedAuthority> convertAuthorities(int role) {
        List<GrantedAuthority> list = new ArrayList<>();
        switch (role) {
            case 0: {
                list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            }
            case 4: {
                list.add(new SimpleGrantedAuthority("ROLE CENSOR"));
                break;
            }
            case 3: {
                list.add(new SimpleGrantedAuthority("ROLE_SINGER"));
                break;
            }
            case 2:{
                list.add(new SimpleGrantedAuthority("ROLE_PREUSER"));
                break;
            }
            case  1:{
                list.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            }
        }
        return list;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
