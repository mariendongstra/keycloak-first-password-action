package com.example.keycloak.events;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class PasswordUpdateEventListenerProviderFactory implements EventListenerProviderFactory {

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new PasswordUpdateEventListenerProvider(session);
    }

    @Override
    public void init(Scope config) {
        // Initialization if needed.
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Post initialization if needed.
    }

    @Override
    public void close() {
        // Cleanup if needed.
    }

    @Override
    public String getId() {
        return "password-update-listener";
    }
}
