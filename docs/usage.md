# Usage

## `@TempDir` with `JimfsTempDirFactory`

The simplest way to use Jimfs JUnit Jupiter is to set the
[`factory`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html#factory())
attribute of `@TempDir` to `JimfsTempDirFactory`:

``` java
--8<--
JimfsTempDirFactoryDemo.java:import
JimfsTempDirFactoryDemo.java:test
--8<--
```

`tempDir` is resolved to an in-memory temporary directory based on Jimfs, configured appropriately for the current
platform.

While the example shows the injection of the temporary directory into a test method parameter,
the same can also be done with class fields and constructor parameters.

## `@JimfsTempDir`

`@JimfsTempDir` is an annotation that can be used as a drop-in replacement for
`@TempDir(factory = JimfsTempDirFactory.class)`:

``` java
--8<--
JimfsTempDirDemo.java:import
JimfsTempDirDemo.java:test-default
--8<--
```

The default behavior of the annotation is equivalent to using `JimfsTempDirFactory` directly: `tempDir` is resolved to
an in-memory temporary directory based on Jimfs, configured appropriately for the current platform.

For better control over the underlying in-memory file system, `@JimfsTempDir` offers an optional `value` attribute
that can be set to the desired configuration, one of:

* `DEFAULT`: based on the corresponding [configuration parameter](configuration-parameters.md#default-jimfs-configuration) (default)
* `FOR_CURRENT_PLATFORM`: appropriate to the current platform
* `OS_X`: for a Mac OS X-like file system
* `UNIX`: for a UNIX-like file system
* `WINDOWS`: for a Windows-like file system

For example, the following defines a Windows-like temporary directory, regardless of the platform on which the test is
running:

``` java
--8<--
JimfsTempDirDemo.java:import
JimfsTempDirDemo.java:import-windows
JimfsTempDirDemo.java:test-windows
--8<--
```
