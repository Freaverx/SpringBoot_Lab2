package ru.proskuryakov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Request {

    @NotBlank(message = "UID is mandatory")
    @Size(max = 32, message = "UID must be up to 32 characters")
    private String uid;

    @NotBlank(message = "Operation UID is mandatory")
    @Size(max = 32, message = "Operation UID must be up to 32 characters")
    private String operationUid;

    private String systemName;

    @NotBlank(message = "System time is mandatory")
    private String systemTime;

    private String source;

    @NotNull(message = "Communication ID is mandatory")
    @Min(value = 1, message = "Communication ID must be at least 1")
    @Max(value = 100000, message = "Communication ID must be at most 100000")
    private Integer communicationId;

    private Integer templateId;
    private Integer productCode;
    private Integer smsCode;

    // Getters and setters
}
