package ru.uminsky.weatheranalyzer.controller;
import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class  WeatherErrorControllerTest {

    @Mock
    private MockHttpServletRequest request;

    @Test
    void handleException_notFound() {
        WeatherErrorController errorController = new WeatherErrorController();
        request = new MockHttpServletRequest();
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());

        String viewName = errorController.handleException(request);

        assertEquals("errors/not-found-error", viewName);
    }

    @Test
    void handleException_internalServerError() {
        WeatherErrorController errorController = new WeatherErrorController();
        request = new MockHttpServletRequest();
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());

        String viewName = errorController.handleException(request);

        assertEquals("errors/error", viewName);
    }

    @Test
    void handleException_defaultError() {
        WeatherErrorController errorController = new WeatherErrorController();
        request = new MockHttpServletRequest();
        // не устанавливаем атрибут ERROR_STATUS_CODE

        String viewName = errorController.handleException(request);

        assertEquals("errors/error", viewName);
    }
}
