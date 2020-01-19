package dev.morling.demos.recordvalidation.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record EmailAddress(@Email @NotNull @Size(min=1, max=250) String value) {

}
