package dev.morling.demos.recordvalidation.implementation;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path.Node;
import javax.validation.Path.ParameterNode;
import javax.validation.Validation;
import javax.validation.Validator;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

public class ValidationInterceptor {

    private static final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public static <T> void validate(@Origin Constructor<T> constructor, @AllArguments Object[] args) {
        Set<ConstraintViolation<T>> violations = validator.forExecutables()
            .validateConstructorParameters(constructor, args);

        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .sorted(ValidationInterceptor::compare)
                    .map(cv -> getParameterName(cv) + " - " + cv.getMessage())
                    .collect(Collectors.joining(System.lineSeparator()));

            throw new ConstraintViolationException("Invalid instantiation of record type " + constructor.getDeclaringClass().getSimpleName() + System.lineSeparator() + message, violations);
        }
    }

    private static int compare(ConstraintViolation<?> o1, ConstraintViolation<?> o2) {
        return Integer.compare(getParameterIndex(o1), getParameterIndex(o2));
    }

    private static String getParameterName(ConstraintViolation<?> cv) {
        Iterator<Node> path = cv.getPropertyPath().iterator();
        while (path.hasNext()) {
            Node node = path.next();
            if (node.getKind() == ElementKind.PARAMETER) {
                return node.getName();
            }
        }

        return "";
    }

    private static int getParameterIndex(ConstraintViolation<?> cv) {
        Iterator<Node> path = cv.getPropertyPath().iterator();
        while (path.hasNext()) {
            Node node = path.next();
            if (node.getKind() == ElementKind.PARAMETER) {
                return node.as(ParameterNode.class).getParameterIndex();
            }
        }

        return -1;
    }
}
