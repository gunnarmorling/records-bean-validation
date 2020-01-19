package dev.morling.demos.recordvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import dev.morling.demos.recordvalidation.model.Car;

/**
 * Unit test for simple App.
 */
public class RecordValidationTest {

    @Test
    public void canValidate() throws Exception {
        Car valid = new Car("Morris", "HH-AB-123", 2);
        System.out.println(valid);

        try {
            Car invalid = new Car("", "HH-AB-123", 1);
            fail("Expected ConstraintViolationException not thrown");
        }
        catch(ConstraintViolationException cve) {
            System.out.println(cve.getMessage());
            assertEquals(2, cve.getConstraintViolations().size());
        }
    }
}
