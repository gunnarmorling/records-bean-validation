package dev.morling.demos.recordvalidation.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import org.hibernate.validator.constraints.ParameterScriptAssert;

public record Car(
    @NotBlank String manufacturer,
    @NotNull @Size(min = 2, max = 14) String licensePlate,
    @Min(2) int seatCount) {

//    @ParameterScriptAssert(lang="javascript", script = "false")
//    public Car {
//
//    }
}
