package com.example.keycloak.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.*;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialManager;
import org.keycloak.credential.UserCredentialStoreManager;
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

        boolean isFirstLogin = isFirstLogin(user, context.getSession(), context.getRealm());

        Response challenge = context.form()
            .setAttribute("isFirstLogin", isFirstLogin)
            .createForm(isFirstLogin ? "first-password.ftl" : "reset-password.ftl");
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
        CredentialManager credentialManager = session.getProvider(CredentialManager.class);
        credentialManager.updateCredential(realm, user, UserCredentialModel.password(password, false));

        context.success();
    }

    private boolean isFirstLogin(UserModel user, KeycloakSession session, RealmModel realm) {
        UserCredentialManager credentialManager = session.userCredentialManager();
        return !credentialManager.isConfiguredFor(realm, user, CredentialModel.PASSWORD);
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
        // No required actions
    }

    @Override
    public void close() {
        // No resources to close
    }
}
