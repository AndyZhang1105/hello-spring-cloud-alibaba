package com.zz.hello.auth.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}