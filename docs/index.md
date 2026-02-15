# Overview

Jimfs JUnit Jupiter is a [JUnit Jupiter](https://junit.org/) extension for in-memory
[`@TempDir`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html) directories
using the [Jimfs](https://github.com/google/jimfs) file system.

It is already possible to use Jimfs and JUnit Jupiter together to create in-memory temporary directories for testing.
However, this requires integrating the Jimfs in-memory file system handling with the framework's
[test lifecycle callbacks](https://docs.junit.org/current/extensions/test-lifecycle-callbacks.html), boilerplate code
that users would have to implement themselves.
This extension offers a smooth integration between the two so that users do not have to take care of it.

## Compatibility

Jimfs JUnit Jupiter is based on JUnit Jupiter 6 and requires Java 17 or higher.

## Getting Started

[![Jimfs JUnit Jupiter](https://img.shields.io/maven-central/v/io.github.scordio/jimfs-junit-jupiter?label=Jimfs%20JUnit%20Jupiter&color=#4cae4f)](https://central.sonatype.com/artifact/io.github.scordio/jimfs-junit-jupiter)

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

For instructions on how to use it with JUnit Jupiter, see the [Usage](usage.md) section.

## First-party Support

The JUnit Jupiter
[`TempDirFactory` SPI](https://docs.junit.org/current/writing-tests/built-in-extensions.html#TempDirectory)
allows libraries like Jimfs to customize how temporary directories are created via the `@TempDir` annotation.
First-party support was requested in [google/jimfs#258](https://github.com/google/jimfs/issues/258).
However, Google has not yet adopted JUnit Jupiter, and first-party support may only be provided once it does.
If that happens, this project will likely be discontinued.
