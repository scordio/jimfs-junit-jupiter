# Jimfs JUnit Jupiter [![Maven Central](https://img.shields.io/maven-central/v/io.github.scordio/jimfs-junit-jupiter?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.scordio/jimfs-junit-jupiter) [![javadoc](https://javadoc.io/badge2/io.github.scordio/jimfs-junit-jupiter/javadoc.svg)](https://javadoc.io/doc/io.github.scordio/jimfs-junit-jupiter)

[![CI](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml?query=branch%3Amain)

This project provides a [JUnit Jupiter](https://junit.org/) extension for in-memory
[`@TempDir`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html) directories
using the [Jimfs](https://github.com/google/jimfs) file system.

## Documentation

- [User Guide](https://scordio.github.io/jimfs-junit-jupiter/)
- [Release Notes](../../releases)

## Motivation

It is currently possible to use Jimfs and JUnit Jupiter together to create in-memory temporary directories for testing.
However, this requires Jimfs in-memory file system handling to be integrated with JUnit Jupiter test lifecycle
callbacks, boilerplate code that users must implement themselves.

Starting from version 5.10, JUnit Jupiter offers a
[`TempDirFactory` SPI](https://junit.org/junit5/docs/5.10.0/user-guide/#writing-tests-built-in-extensions-TempDirectory)
for customizing how temporary directories are created via the `@TempDir` annotation.
The SPI allows libraries like Jimfs to provide their own implementations.

First-party support was requested in [google/jimfs#258](https://github.com/google/jimfs/issues/258).
However, Google has not yet started using JUnit Jupiter, and first-party support may only be provided once it does.
Since this extension was created to offer users a smooth integration between Jimfs and JUnit Jupiter, the project will
likely be discontinued if Google ever provides official support for this integration.

## License

Jimfs JUnit Jupiter is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
