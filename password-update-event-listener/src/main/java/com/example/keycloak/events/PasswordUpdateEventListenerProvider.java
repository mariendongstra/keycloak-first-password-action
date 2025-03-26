package com.example.keycloak.events;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
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
            UserModel user = session.users().getUserById(userId, session.getContext().getRealm());
            if (user != null) {
                // Example: Set emailVerified to true
                user.setEmailVerified(true);
                // You can also set a custom attribute:
                user.setSingleAttribute("customAttribute", "customValue");
            }
        }
    }

    @Override
    public void close() {
        // Clean up if necessary
    }
}
