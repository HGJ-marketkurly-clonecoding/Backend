package com.marketkurly.security;

import com.marketkurly.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    // 유저 정보를 조회하기 위한 클래스
    // UserDetailsService에서 UserDetails 타입으로 반환하기 위하여

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    //인증관리자가 대조할때 쓰는 메서드

    public String getName(){return user.getUsername();}
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    //인증관리자가 대조할때 쓰는 메서드
    @Override
    public String getUsername() {
        return user.getEmail();
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


    //스프링 시큐리티의 권한 파악 (유저권한으로 주소창에 api 주소치면 관리자로 접속되던 문제 해결)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.emptyList();
    }
}
