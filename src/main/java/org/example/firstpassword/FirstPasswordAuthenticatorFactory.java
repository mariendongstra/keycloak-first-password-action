package org.example.firstpassword;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.Config;
import org.keycloak.authentication.AuthenticationExecutionModel.Requirement;

import java.util.List;

public class FirstPasswordAuthenticatorFactory implements AuthenticatorFactory {

    public static final String ID = "first-password-authenticator";

    public enum SimpleRequirement {
        REQUIRED, ALTERNATIVE, DISABLED
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new FirstPasswordAuthenticator();
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getDisplayType() {
        return "First Password Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Triggers an action when a user sets their password for the first time.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    // Instead of Requirement enum (removed in modern Keycloak)
    @Override
    public String getReferenceCategory() {
        return "first-password";
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[] {
            Requirement.REQUIRED,
            Requirement.DISABLED
        };
    }
}