package dev.morling.demos.recordvalidation.implementation;

import static net.bytebuddy.matcher.ElementMatchers.annotationType;
import static net.bytebuddy.matcher.ElementMatchers.hasAnnotation;

import java.io.IOException;

import javax.validation.Constraint;

import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;

public class ValidationWeavingPlugin implements Plugin {

    @Override
    public boolean matches(TypeDescription target) {
        return target.getDeclaredMethods()
                .stream()
                .filter(m -> m.isConstructor() && isConstrained(m))
                .findFirst()
                .isPresent();
    }

    @Override
    public Builder<?> apply(Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        return builder.constructor(this::isConstrained)
                .intercept(SuperMethodCall.INSTANCE.andThen(
                        MethodDelegation.to(ValidationInterceptor.class)));
    }

    private boolean isConstrained(MethodDescription method) {
        return hasConstrainedReturnValue(method) || hasConstrainedParameter(method);
    }

    private boolean hasConstrainedReturnValue(MethodDescription method) {
        return !method.getDeclaredAnnotations()
                .asTypeList()
                .filter(hasAnnotation(annotationType(Constraint.class)))
                .isEmpty();
    }

    private boolean hasConstrainedParameter(MethodDescription method) {
        return method.getParameters()
                .asDefined()
                .stream()
                .filter(p -> isConstrained(p))
                .findFirst()
                .isPresent();
    }

    private boolean isConstrained(ParameterDescription.InDefinedShape parameter) {
        return !parameter.getDeclaredAnnotations()
                .asTypeList()
                .filter(hasAnnotation(annotationType(Constraint.class)))
                .isEmpty();
    }

    @Override
    public void close() throws IOException {
    }
}