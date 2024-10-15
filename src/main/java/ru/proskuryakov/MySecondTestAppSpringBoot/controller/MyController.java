package ru.proskuryakov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.proskuryakov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.proskuryakov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.proskuryakov.MySecondTestAppSpringBoot.model.Request;
import ru.proskuryakov.MySecondTestAppSpringBoot.model.Response;
import ru.proskuryakov.MySecondTestAppSpringBoot.service.ValidationService;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if ("123".equals(request.getUid())){
            throw new UnsupportedCodeException("UID '123' is not supported");
        }

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UnsupportedCodeException.class)
    public ResponseEntity<Response> handleUnsupportedCodeException(UnsupportedCodeException e) {
        Response response = Response.builder()
                .code("failed")
                .errorCode("UnsupportedCodeException")
                .errorMessage(e.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
