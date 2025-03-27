package org.example.firstpassword;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;

public class FirstPasswordAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Just show a basic page for demo
        Response challenge = context.form()
            .setAttribute("message", "This is the FirstPasswordAuthenticator step")
            .createForm("login.ftl");
        context.challenge(challenge);
    }

    @Override public void action(AuthenticationFlowContext context) {
        // Accept everything for now
        context.success();
    }

    @Override public boolean requiresUser() { return false; }
    @Override public boolean configuredFor(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) { return true; }
    @Override public void setRequiredActions(org.keycloak.models.KeycloakSession session, org.keycloak.models.RealmModel realm, UserModel user) {}
    @Override public void close() {}
}