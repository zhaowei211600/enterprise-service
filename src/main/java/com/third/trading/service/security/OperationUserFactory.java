package com.third.trading.service.security;

import com.third.trading.bean.OperationRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class OperationUserFactory {

    private OperationUserFactory(){

    }

    public static JwtUser create(com.third.trading.bean.OperationUser operationUser){
        return new JwtUser(
                operationUser.getId(),
                operationUser.getAccount(),
                operationUser.getPassword(),
                operationUser.getContactPhone(),
                mapToGrantedAuthorities(operationUser.getRoleList())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<OperationRole> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority;
        for (OperationRole authority : authorities){
            grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }
}
