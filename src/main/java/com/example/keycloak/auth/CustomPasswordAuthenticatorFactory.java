package com.example.keycloak.auth;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;

import java.util.List;

public class CustomPasswordAuthenticatorFactory implements ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "custom-password-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Password Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Handles first-time password setup and password reset with distinct screens.";
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new CustomPasswordAuthenticator();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialization code if needed
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Post-initialization code if needed
    }

    @Override
    public void close() {
        // Cleanup code if needed
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getReferenceCategory() {
        return "password";
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
            AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }
}
