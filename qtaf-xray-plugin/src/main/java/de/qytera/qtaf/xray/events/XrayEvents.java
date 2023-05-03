package de.qytera.qtaf.xray.events;

import de.qytera.qtaf.xray.dto.request.xray.ImportExecutionResultsRequestDto;
import de.qytera.qtaf.xray.dto.response.xray.ImportExecutionResultsResponseDto;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rx.subjects.BehaviorSubject;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XrayEvents {
    /**
     * Subject that emits events when Xray ImportDTO object is built.
     */
    public static final BehaviorSubject<ImportExecutionResultsRequestDto> importDtoCreated = BehaviorSubject.create();

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
    public static final BehaviorSubject<Response> authenticationResponseAvailable = BehaviorSubject.create();

    /**
     * Subject that emits events when Xray upload response is available.
     */
    public static final BehaviorSubject<Response> uploadResponseAvailable = BehaviorSubject.create();

    /**
     * Subject that emits events when all tests are finished.
     */
    public static final BehaviorSubject<ImportExecutionResultsResponseDto> responseDtoAvailable = BehaviorSubject.create();
}
