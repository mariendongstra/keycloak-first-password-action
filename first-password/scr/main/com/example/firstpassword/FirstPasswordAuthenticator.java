package com.example.firstpassword;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.Response;

public class FirstPasswordAuthenticator implements Authenticator {

    public static final String PROVIDER_ID = "first-password";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Render the form using the custom template
        Response challenge = context.form()
                .setTemplateName("login-first-password")
                .createForm();
        context.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Retrieve the submitted new password
        String password = context.getHttpRequest().getDecodedFormParameters().getFirst("password");
        if (password == null || password.isEmpty()) {
            Response challenge = context.form()
                    .setError("missingPassword")
                    .setTemplateName("login-first-password")
                    .createForm();
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }
        
        // Here you would update the user’s password (this is a placeholder).
        UserModel user = context.getUser();
        // Example: update password via user.credentialManager() if needed
        // For demonstration, we mark that the first password has been set.
        user.setSingleAttribute("firstPasswordSet", "true");

        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(org.keycloak.models.RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(org.keycloak.models.RealmModel realm, UserModel user) {
        // No additional required actions.
    }

    @Override
    public void close() {
        // No resources to release.
    }
}
