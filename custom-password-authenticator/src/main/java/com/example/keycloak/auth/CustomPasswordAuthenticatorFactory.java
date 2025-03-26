package com.example.keycloak.auth;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.AuthenticatorUtil;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

public class CustomPasswordAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "custom-password-authenticator";

    @Override
    public Authenticator create(KeycloakSession session) {
        return new CustomPasswordAuthenticator();
    }

    @Override
    public void init(Scope config) {
        // Initialization if needed.
    }

    @Override
    public void postInit(KeycloakSession session) {
        // Post-initialization if needed.
    }

    @Override
    public void close() {
        // Cleanup if needed.
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Password Flow Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Handles first password change and forgotten password flows by selecting different templates.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[] {
            AuthenticationExecutionModel.Requirement.REQUIRED
        };
    }

    @Override
    public void postInit(KeycloakSessionFactory arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'postInit'");
    }

    @Override
    public String getReferenceCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferenceCategory'");
    }

    @Override
    public boolean isConfigurable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isConfigurable'");
    }
}
