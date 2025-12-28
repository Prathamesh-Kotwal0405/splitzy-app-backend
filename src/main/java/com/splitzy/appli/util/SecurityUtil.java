package com.splitzy.appli.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.splitzy.appli.entities.UserEntity;
import com.splitzy.appli.exception.UnauthorizedException;

public class SecurityUtil {
    public static UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserEntity user) {
            return user;
        }
        throw new UnauthorizedException("Unauthorized");
    }
}
