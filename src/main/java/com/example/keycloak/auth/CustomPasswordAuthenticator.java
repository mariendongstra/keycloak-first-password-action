package com.example.keycloak.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.UserModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.services.validation.Validation;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class CustomPasswordAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        if (user == null) {
            context.failure(AuthenticationFlowError.UNKNOWN_USER);
            return;
        }

        boolean hasPassword = user.credentialManager().isConfiguredFor("password");

        String template = hasPassword ? "reset-password.ftl" : "first-password.ftl";

        Response challenge = context.form()
            .setAttribute("isFirstLogin", !hasPassword)
            .createForm(template);

        context.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String password = formData.getFirst(Validation.FIELD_PASSWORD);
        String passwordConfirm = formData.getFirst(Validation.FIELD_PASSWORD_CONFIRM);

        if (Validation.isBlank(password) || !password.equals(passwordConfirm)) {
            Response challenge = context.form()
                .setError("Passwords do not match or are empty")
                .createForm("reset-password.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        UserModel user = context.getUser();
        user.credentialManager().updateCredential(PasswordCredentialModel.createFromValue(password));
        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Nothing here
    }

    @Override
    public void close() {
        // Nothing to clean up
    }
}
