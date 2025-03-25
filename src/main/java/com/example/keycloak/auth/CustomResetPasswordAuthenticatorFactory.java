package com.example.keycloak.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class CustomResetPasswordAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "custom-reset-password";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Reset Password";
    }

    @Override
    public String getHelpText() {
        return "A custom reset password step using a custom FTL template.";

    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new CustomResetPasswordAuthenticator();
    }

    @Override public void init(org.keycloak.Config.Scope config) {}
    @Override public void postInit(KeycloakSessionFactory factory) {}
    @Override public void close() {}
    @Override public boolean isConfigurable() { return false; }
    @Override public boolean isUserSetupAllowed() { return false; }
    @Override public Requirement[] getRequirementChoices() {
        return new Requirement[] { Requirement.REQUIRED };
    }

    @Override public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }
}
