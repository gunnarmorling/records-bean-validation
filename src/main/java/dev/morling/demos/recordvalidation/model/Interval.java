package dev.morling.demos.recordvalidation.model;

import org.hibernate.validator.constraints.ParameterScriptAssert;

public record Interval(int begin, int end) {

    @ParameterScriptAssert(lang="javascript", script="end > begin")
    public Interval {
    }
}
