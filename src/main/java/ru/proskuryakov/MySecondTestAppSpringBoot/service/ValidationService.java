package ru.proskuryakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.proskuryakov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
