# Enforcing Java 14 Record Invariants with Bean Validation

Example code accompanying the [blog post](https://www.morling.dev/blog/enforcing-java-record-invariants-with-bean-validation/) "Enforcing Java Record Invariants With Bean Validation".
It shows how to apply Bean Validation constraints to the components of record types and automatically validate them upon constructor invocation,
by means of injecting logic for invoking Bean Validation's method validation API and raising an exception in case of any violations.

![Enforcing Java 14 Record Invariants with Bean Validation](records-bean-validation.gif)

## Build

Run the following to build this project:

```shell
export MAVEN_OPTS="--enable-preview"
mvn clean package
```

## License

This code base is available ander the Apache License, version 2.

