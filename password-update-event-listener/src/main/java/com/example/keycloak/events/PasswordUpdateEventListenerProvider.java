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
            // Retrieve the current realm from the session context
            RealmModel realm = session.getContext().getRealm();
            UserModel user = session.users().getUserById(userId, realm);
            if (user != null) {
                // Example: Automatically mark the user's email as verified
                user.setEmailVerified(true);
                // Also set a custom attribute if needed
                user.setSingleAttribute("customAttribute", "customValue");
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // No admin event processing is needed for this provider.
    }

    @Override
    public void close() {
        // Clean up resources if necessary.
    }
}
