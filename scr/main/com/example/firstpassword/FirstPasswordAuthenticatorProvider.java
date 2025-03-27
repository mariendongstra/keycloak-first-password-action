package org.example.keycloak;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.resetcred.ResetCredentials;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class FirstPasswordAuthenticatorProvider extends ResetCredentials {

    public FirstPasswordAuthenticatorProvider(KeycloakSession session) {
        super(session);
    }

    @Override
    public void resetPassword(AuthenticationFlowContext context) {
        super.resetPassword(context);
    }

    @Override
    public Response createResetCredentialForm(AuthenticationFlowContext context) {
        return context.form().createForm("first-password.ftl");
    }

    @Override
    public String getDisplayType() {
        return "First Password Provider";
    }

    @Override
    public String getId() {
        return "first-password-provider";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return super.getConfigProperties();
    }
}