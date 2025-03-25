package com.example.keycloak.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class CustomResetPasswordAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response challenge = context.form()
            .createForm("forgot-password.ftl");
        context.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String password = formData.getFirst("password");

        if (password == null || password.trim().isEmpty()) {
            Response challenge = context.form()
                .setError("Missing password")
                .createForm("forgot-password.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        UserModel user = context.getUser();
        context.getSession().userCredentialManager().updateCredential(
            context.getRealm(), user, UserCredentialModel.password(password, false)
        );

        context.success();
    }

    @Override public void close() {}
    @Override public boolean requiresUser() { return true; }
    @Override public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) { return true; }
    @Override public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}
}
