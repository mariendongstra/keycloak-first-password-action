package org.example.firstpassword;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import java.util.List;
import org.keycloak.provider.ProviderConfigProperty;

public class FirstPasswordAuthenticatorFactory implements AuthenticatorFactory {

    public static final String ID = "first-password-authenticator";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new FirstPasswordAuthenticator();
    }

    @Override public void init(Config.Scope config) {}
    @Override public void postInit(KeycloakSessionFactory factory) {}
    @Override public void close() {}
    @Override public String getDisplayType() {
        return "First Password Authenticator";
    }

    @Override public String getHelpText() {
        return "Shows a custom screen on first password set.";
    }

    @Override public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    @Override public boolean isConfigurable() { return false; }
    @Override public boolean isUserSetupAllowed() { return false; }
    @Override public Requirement[] getRequirementChoices() {
        return new Requirement[] {
            Requirement.REQUIRED,
            Requirement.DISABLED
        };
    }
}