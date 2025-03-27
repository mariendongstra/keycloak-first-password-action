package com.example.keycloak;

import org.keycloak.authentication.authenticators.resetcred.ResetCredentials;
import org.keycloak.models.KeycloakSession;
import org.keycloak.theme.Theme;

import jakarta.ws.rs.core.Response;

public class FirstPasswordAuthenticator extends ResetCredentials {

    public FirstPasswordAuthenticator(KeycloakSession session) {
        super(session);
    }

    @Override
    protected Response setResetCredentialError(String errorMessage) {
        // Override to use the custom template "first-password.ftl"
        try {
            Theme theme = session.theme().getTheme(Theme.Type.LOGIN);
            return Response.ok(theme.getResourceAsStream("first-password.ftl") != null 
                ? renderForm("first-password.ftl", errorMessage) 
                : renderForm("reset-credentials.ftl", errorMessage)).build();
        } catch (Exception e) {
            return super.setResetCredentialError(errorMessage);
        }
    }
}