# Configuration Parameters

Jimfs JUnit Jupiter supports JUnit
[configuration parameters](https://junit.org/junit5/docs/current/user-guide/#running-tests-config-params).

## Default `@TempDir` Factory

The `junit.jupiter.tempdir.factory.default` configuration parameter sets the default factory to use, expecting a fully
qualified class name.

For example, the following configures `JimfsTempDirFactory`:

```properties
junit.jupiter.tempdir.factory.default=io.github.scordio.jimfs.junit.jupiter.JimfsTempDirFactory
```

The factory will be used for all `@TempDir` annotations unless the annotation's
[`factory`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html#factory())
attribute specifies a different type.

## Default Jimfs Configuration

<!-- md:version 0.2.0 -->
<!-- md:default `indigo` -->

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

All Jimfs-based temporary directories will be configured accordingly unless [`@JimfsTempDir`](usage.md#jimfstempdir) is used with its `value`
attribute set.
