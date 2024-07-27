# Jimfs JUnit Jupiter

[![CI](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml?query=branch%3Amain)
[![Cross-Version](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/cross-version.yml/badge.svg?branch=main)](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/cross-version.yml?query=branch%3Amain)

This project provides a [JUnit Jupiter][] extension module that adds support for `@TempDir` directories based on the in-memory file system [Jimfs][].

## Motivation

Starting from [version 5.10](https://junit.org/junit5/docs/5.10.0/release-notes/index.html#release-notes), JUnit 5 offers a [`TempDirFactory` SPI](https://junit.org/junit5/docs/5.10.0/user-guide/#writing-tests-built-in-extensions-TempDirectory) for customizing how temporary directories are created via the `@TempDir` annotation.
The SPI allows libraries like Jimfs to provide their own implementation.

According to [google/jimfs#258](https://github.com/google/jimfs/issues/258), Google is currently not a JUnit 5 user and first-party support might be provided only once Google moves to JUnit 5.

For this reason, I decided to implement this extension to aid all the users that would like a smooth integration between Jimfs and JUnit Jupiter.
If Google ever offers first-party support for this integration, this project will likely be discontinued.

## Compatibility

Jimfs JUnit Jupiter is based on JUnit Jupiter 5, thus requiring at least Java 8.
Compatibility is guaranteed only with the JUnit Jupiter versions from 5.10 to the latest.

## Getting Started

### Maven

```xml
<dependency>
  <groupId>io.github.scordio</groupId>
  <artifactId>jimfs-junit-jupiter</artifactId>
  <version>${jimfs-junit-jupiter.version}</version>
</dependency>
```

### Gradle

```kotlin
implementation("io.github.scordio:jimfs-junit-jupiter:${jimfsJunitJupiterVersion}")
```

### How To

## License

Jimfs JUnit Jupiter is released under version 2.0 of the [Apache License][].

[Apache License]: https://www.apache.org/licenses/LICENSE-2.0
[Jimfs]: https://github.com/google/jimfs
[JUnit Jupiter]: https://github.com/junit-team/junit5
