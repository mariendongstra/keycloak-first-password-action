package com.example.firstpassword;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.Config;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class FirstPasswordAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "first-password";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new FirstPasswordAuthenticator();
    }

    @Override
    public String getDisplayType() {
        return "First Password Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Authenticator for first password setup using a custom template.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void init(Config.Scope config) {
        // No initialization required.
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // No post-initialization actions.
    }

    @Override
    public void close() {
        // Nothing to close.
    }
}
