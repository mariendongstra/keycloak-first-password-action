package org.example.keycloak;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class FirstPasswordAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        context.getSession().getContext().getAuthenticationSession()
                .setAuthNote("CUSTOM_AUTH", "first-password");
        context.form().setAttribute("username", context.getUser().getUsername());
        context.challenge(context.form().createForm("first-password.ftl"));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Delegate to default reset credentials behavior
        context.getSession().getProvider(FirstPasswordAuthenticatorProvider.class)
                .resetPassword(context);
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
    }

    @Override
    public void close() {
    }
}