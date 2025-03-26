package com.example.keycloak.auth;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.credential.UserCredentialManager;

import jakarta.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class CustomPasswordAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Determine which flow is being used based on a parameter, client note, etc.
        // For instance, you might check for a note that distinguishes "firstPassword"
        String flowIndicator = context.getAuthenticationSession().getClientNote("passwordFlow");

        // Choose template based on the flow indicator
        String template;
        if ("firstPassword".equals(flowIndicator)) {
            template = "custom-first-password.ftl"; // your custom template for first password change
        } else {
            template = "custom-forgot-password.ftl"; // your custom template for forgotten password
        }

        // Set a form attribute if needed (optional, for use in the template)
        context.getAuthenticationSession().setAuthNote("flowType", flowIndicator);

        // Create and send the challenge using the chosen template
        jakarta.ws.rs.core.Response challenge = context.form().createForm(template);
        context.challenge(challenge);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String password = formData.getFirst("password");
        if (password == null || password.isEmpty()) {
            Response challenge = context.form()
                .setError("Password cannot be empty")
                .createForm("error.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }
        
        UserModel user = context.getUser();
        // Retrieve the credential manager using getProvider() instead of a direct method.
        UserCredentialManager ucm = context.getSession().getProvider(UserCredentialManager.class);
        
        boolean updated = ucm.updateCredential(context.getRealm(), user, UserCredentialModel.password(password));
        if (!updated) {
            Response challenge = context.form()
                .setError("Failed to update password")
                .createForm("error.ftl");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, challenge);
            return;
        }
        context.success();
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        // As this is part of a required action, you might return true by default.
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // No required actions to set here.
    }

    @Override
    public void close() {
        // Cleanup if necessary.
    }
}
