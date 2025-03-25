package com.example.keycloak.action;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.models.credential.CredentialModel;
import org.keycloak.models.utils.KeycloakModelUtils;

public class FirstTimePasswordAction implements RequiredActionProvider {

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // No automatic trigger
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        Response challenge = context.form()
            .setAttribute("isFirstTime", true)
            .createForm("first-password.ftl");

        context.challenge(challenge);
    }

    @Override
    public void processAction(RequiredActionContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        String password = formData.getFirst("password");

        if (password == null || password.trim().isEmpty()) {
            Response challenge = context.form()
                .setError("missingPassword")
                .createForm("first-password.ftl");

            context.challenge(challenge);
            return;
        }

        UserModel user = context.getUser();
        RealmModel realm = context.getRealm();

        PasswordCredentialModel passwordCredential = PasswordCredentialModel.createFromPlainText(password);
        context.getSession().userCredentialManager().updateCredential(realm, user, passwordCredential);

        context.success();
    }

    @Override
    public void close() {}
}
