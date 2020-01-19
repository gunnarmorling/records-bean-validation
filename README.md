# Java 14 Record Invariants with Bean Validation

Example code accompanying the blog post "Enforcing Java Record Invariants With Bean Validation".
It shows how to apply Bean Validation constraints to the components of record types and automatically validate them upon constructor invocation,
by means of injecting logic for invoking Bean Validation's method validation API and raising an exception in case of any violations.

## Build

Run the following to build this project:

```shell
mvn clean package
```

## License

This code base is available ander the Apache License, version 2.

