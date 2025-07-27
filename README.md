# Jimfs JUnit Jupiter [![Maven Central](https://img.shields.io/maven-central/v/io.github.scordio/jimfs-junit-jupiter?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.scordio/jimfs-junit-jupiter) [![javadoc](https://javadoc.io/badge2/io.github.scordio/jimfs-junit-jupiter/javadoc.svg)](https://javadoc.io/doc/io.github.scordio/jimfs-junit-jupiter)

[![CI](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/jimfs-junit-jupiter/actions/workflows/main.yml?query=branch%3Amain)

This project provides a [JUnit Jupiter][] extension for in-memory
[`@TempDir`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html)
directories using the [Jimfs][] file system.

## Getting Started

### Compatibility

Jimfs JUnit Jupiter is based on JUnit Jupiter 5 and requires Java 8 or higher.

Compatibility is guaranteed only with the JUnit Jupiter versions from
[5.10.0](https://junit.org/junit5/docs/5.10.0/release-notes/index.html)
to the
[latest](https://junit.org/junit5/docs/current/release-notes/index.html).

### Maven

```xml
<dependency>
  <groupId>io.github.scordio</groupId>
  <artifactId>jimfs-junit-jupiter</artifactId>
  <version>${jimfs-junit-jupiter.version}</version>
  <scope>test</scope>
</dependency>
```

### Gradle

```kotlin
testImplementation("io.github.scordio:jimfs-junit-jupiter:${jimfsJunitJupiterVersion}")
```

## JimfsTempDirFactory

The simplest usage is to set the
[`factory`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html#factory())
attribute of `@TempDir` to `JimfsTempDirFactory`:

```java
@Test
void test(@TempDir(factory = JimfsTempDirFactory.class) Path tempDir) {
  assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
}
```

`tempDir` is resolved to an in-memory temporary directory based on Jimfs, configured appropriately for the current
platform.

## @JimfsTempDir

`@JimfsTempDir` is an annotation that can be used as a drop-in replacement for
`@TempDir(factory = JimfsTempDirFactory.class)`:

```java
@Test
void test(@JimfsTempDir Path tempDir) {
  assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
}
```

The default behavior of the annotation is equivalent to using `JimfsTempDirFactory` directly: `tempDir` is resolved to
an in-memory temporary directory based on Jimfs, configured appropriately for the current platform.

For better control over the underlying in-memory file system, `@JimfsTempDir` offers an optional `value` attribute
that can be set to the desired configuration, one of:
* `DEFAULT`: based on the corresponding [configuration parameter](#default-jimfs-configuration) (default)
* `FOR_CURRENT_PLATFORM`: appropriate to the current platform
* `OS_X`: for a Mac OS X-like file system
* `UNIX`: for a UNIX-like file system
* `WINDOWS`: for a Windows-like file system

For example, the following defines a Windows-like temporary directory, regardless of the platform on which the test is
running:

```java
@Test
void test(@JimfsTempDir(WINDOWS) Path tempDir) {
  assertThat(tempDir.getFileSystem().getSeparator()).isEqualTo("\\");
}
```

## Configuration Parameters

Jimfs JUnit Jupiter supports JUnit
[configuration parameters](https://junit.org/junit5/docs/current/user-guide/#running-tests-config-params).

### Default `@TempDir` Factory

The `junit.jupiter.tempdir.factory.default` configuration parameter sets the default factory to use, expecting a fully
qualified class name.

For example, the following configures `JimfsTempDirFactory`:

```properties
junit.jupiter.tempdir.factory.default=io.github.scordio.jimfs.junit.jupiter.JimfsTempDirFactory
```

The factory will be used for all `@TempDir` annotations unless the annotation's `factory` attribute specifies a
different type.

### Default Jimfs Configuration

The `jimfs.junit.jupiter.tempdir.configuration.default` configuration parameter sets the default Jimfs configuration to
use. It expects one of the following values (case-insensitive):
* `FOR_CURRENT_PLATFORM`: appropriate to the current platform (default)
* `OS_X`: for a Mac OS X-like file system
* `UNIX`: for a UNIX-like file system
* `WINDOWS`: for a Windows-like file system

For example, the following defines a Windows-like temporary directory, regardless of the platform on which the test is
running:

```properties
jimfs.junit.jupiter.tempdir.configuration.default=windows
```

All Jimfs-based temporary directories will be configured accordingly unless `@JimfsTempDir` is used with
its `value` attribute set.

## Limitations

Jimfs JUnit Jupiter only supports annotated fields or parameters of type `Path`, since Jimfs is a non-default file
system and `File` instances can only be associated with the default file system.

In addition, compared to the configuration options that Jimfs provides, Jimfs JUnit Jupiter offers a simplified
interface to keep usage straightforward.

In case something is missing for your use case, please [raise an issue](../../issues/new)!

## Motivation

It is currently possible to use Jimfs and JUnit Jupiter together to create in-memory temporary directories for testing.
However, this requires Jimfs in-memory file system handling to be integrated with JUnit Jupiter test lifecycle callbacks,
boilerplate code that users must implement themselves.

Starting from version 5.10, JUnit Jupiter offers a
[`TempDirFactory` SPI](https://junit.org/junit5/docs/5.10.0/user-guide/#writing-tests-built-in-extensions-TempDirectory)
for customizing how temporary directories are created via the `@TempDir` annotation.
The SPI allows libraries like Jimfs to provide their own implementations.

First-party support was requested in [google/jimfs#258](https://github.com/google/jimfs/issues/258).
However, Google has not yet started using JUnit Jupiter, and first-party support may only be provided once it does.
Since this extension was created to offer users a smooth integration between Jimfs and JUnit Jupiter, the project will
likely be discontinued if Google ever provides official support for this integration.

## License

Jimfs JUnit Jupiter is released under version 2.0 of the [Apache License][].

[Apache License]: https://www.apache.org/licenses/LICENSE-2.0
[Jimfs]: https://github.com/google/jimfs
[JUnit Jupiter]: https://github.com/junit-team/junit5
