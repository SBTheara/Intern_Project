package com.intern.project.component;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtTokenConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> realmRole= (List<String>) jwt.getClaimAsMap("realm_access").get("roles");
        if(realmRole==null){
            return Collections.emptyList();
        }
        return realmRole.stream().map(role->"ROLE_"+role.toUpperCase()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
