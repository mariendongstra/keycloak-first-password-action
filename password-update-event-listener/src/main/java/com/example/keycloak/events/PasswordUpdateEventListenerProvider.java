package com.example.keycloak.events;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class PasswordUpdateEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;

    public PasswordUpdateEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.UPDATE_PASSWORD) {
            String userId = event.getUserId();
            // Retrieve the current realm using the realm ID from context
            String realmId = session.getContext().getRealmId();
            RealmModel realm = session.realms().getRealm(realmId);
            UserModel user = session.users().getUserById(userId, realm);
            if (user != null) {
                // Example: Set emailVerified to true
                user.setEmailVerified(true);
                // Example: Set a custom attribute
                user.setSingleAttribute("customAttribute", "customValue");
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // No admin event processing needed for this provider.
    }

    @Override
    public void close() {
        // Clean up any resources if needed.
    }
}
