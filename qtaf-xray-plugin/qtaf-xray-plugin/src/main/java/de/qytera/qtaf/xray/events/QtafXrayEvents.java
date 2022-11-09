package de.qytera.qtaf.xray.events;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import rx.subjects.BehaviorSubject;

public class QtafXrayEvents {
    /**
     * Subject that emits events when Xray WebResource object is built.
     */
    public static final BehaviorSubject<WebResource> webResourceAvailable = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray ImportDTO object is built.
     */
    public static final BehaviorSubject<XrayImportRequestDto> importDtoCreated = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray authentication is done.
     */
    public static final BehaviorSubject<Boolean> authenticationSuccess = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray Upload is done.
     */
    public static final BehaviorSubject<Boolean> uploadStatus = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray authentication response is available.
     */
    public static final BehaviorSubject<ClientResponse> authenticationResponseAvailable = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray upload response is available.
     */
    public static final BehaviorSubject<ClientResponse> uploadResponseAvailable = BehaviorSubject.create();

    /**
     * Subject that emits events when all tests are finished.
     */
    public static final BehaviorSubject<XrayImportResponseDto> responseDtoAvailable = BehaviorSubject.create();
}
