package com.example.keycloak.auth;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.ResetPassword;
import jakarta.ws.rs.core.Response;

public class CustomResetPasswordAuthenticator extends ResetPassword {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response challenge = context.form()
            .createForm("forgot-password.ftl");
        context.challenge(challenge);
    }

    @Override
    public void close() {}
}
