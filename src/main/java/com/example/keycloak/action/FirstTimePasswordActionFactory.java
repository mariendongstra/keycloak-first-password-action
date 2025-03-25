package com.example.keycloak.action;

import java.util.Collections;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class FirstTimePasswordActionFactory implements RequiredActionFactory {

    public static final String ID = "first-password-action";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayText() {
        return "First-time Password Setup";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new FirstTimePasswordAction();
    }

    @Override public void init(Config.Scope config) {}
    @Override public void postInit(KeycloakSessionFactory factory) {}
    @Override public void close() {}
    @Override public boolean isOneTimeAction() { return true; }
    @Override public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }
}
