# Overview

Jimfs JUnit Jupiter is a [JUnit Jupiter](https://junit.org/) extension for in-memory
[`@TempDir`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html) directories
using the [Jimfs](https://github.com/google/jimfs) file system.

## Compatibility

Jimfs JUnit Jupiter is based on JUnit Jupiter 5 and requires Java 8 or higher.

Compatibility is guaranteed only with the JUnit Jupiter versions from
[5.10.0](https://junit.org/junit5/docs/5.10.0/release-notes/index.html)
to the
[latest](https://junit.org/junit5/docs/current/release-notes/index.html).

## Getting Started

[![Jimfs JUnit Jupiter](https://img.shields.io/maven-central/v/io.github.scordio/jimfs-junit-jupiter?label=Jimfs%20JUnit%20Jupiter)](https://central.sonatype.com/artifact/io.github.scordio/jimfs-junit-jupiter)

=== ":simple-apachemaven: Maven"

    ``` xml
    <dependency>
      <groupId>io.github.scordio</groupId>
      <artifactId>jimfs-junit-jupiter</artifactId>
      <version>${jimfs-junit-jupiter.version}</version>
      <scope>test</scope>
    </dependency>
    ```

=== ":simple-gradle: Gradle"

    ``` kotlin
    testImplementation("io.github.scordio:jimfs-junit-jupiter:${jimfsJunitJupiterVersion}")
    ```
