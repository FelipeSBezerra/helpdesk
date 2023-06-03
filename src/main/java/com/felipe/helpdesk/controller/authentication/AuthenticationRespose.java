package com.felipe.helpdesk.controller.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRespose {

    private String tokenJwt;
}
