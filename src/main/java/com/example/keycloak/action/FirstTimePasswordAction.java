package com.example.keycloak.action;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;
import org.keycloak.credential.UserCredentialModel;

public class FirstTimePasswordAction implements RequiredActionProvider {

    @Override
    public void evaluateTriggers(RequiredActionContext context) {}

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
        context.getSession().userCredentialManager().updateCredential(
            context.getRealm(), user, UserCredentialModel.password(password, false)
        );
        context.success();
    }

    @Override
    public void close() {}
}
