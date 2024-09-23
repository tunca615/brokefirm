package com.ing.brokefirm.util;

import com.ing.brokefirm.constant.Constant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public class Util {
    public static boolean isAdmin(UsernamePasswordAuthenticationToken principal) {
        return !((List<?>) principal.getAuthorities()).get(0).toString().equals(Constant.ROLE_ADMIN);
    }
}
