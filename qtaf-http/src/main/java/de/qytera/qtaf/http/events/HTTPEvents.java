package de.qytera.qtaf.http.events;

import jakarta.ws.rs.client.WebTarget;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rx.subjects.BehaviorSubject;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HTTPEvents {
    /**
     * Subject that emits events when a {@link WebTarget} object is built.
     */
    public static final BehaviorSubject<WebTarget> webResourceAvailable = BehaviorSubject.create();
}
