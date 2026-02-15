# Configuration Parameters

Jimfs JUnit Jupiter supports JUnit
[configuration parameters](https://docs.junit.org/current/running-tests/configuration-parameters.html).

## Default `@TempDir` Factory

The default factory to use across all `@TempDir` annotated elements can be set via the
`junit.jupiter.tempdir.factory.default` configuration parameter, providing a fully qualified class name.

For example, the following configures `JimfsTempDirFactory`:

```properties
junit.jupiter.tempdir.factory.default=io.github.scordio.jimfs.junit.jupiter.JimfsTempDirFactory
```

The factory will be used for all `@TempDir` annotations unless the annotation's
[`factory`](https://docs.junit.org/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html#factory())
attribute specifies a different type.

## Default Jimfs Configuration

The default Jimfs configuration for all [`JimfsTempDirFactory`](usage.md#tempdir-with-jimfstempdirfactory) and
[`@JimfsTempDir`](usage.md#jimfstempdir) usages can be set via the `jimfs.junit.jupiter.tempdir.configuration.default`
configuration parameter.

One of the following values is expected (case-insensitive):

* `FOR_CURRENT_PLATFORM`: appropriate to the current platform (default)
* `OS_X`: for a Mac OS X-like file system
* `UNIX`: for a UNIX-like file system
* `WINDOWS`: for a Windows-like file system

For example, the following defines a Windows-like temporary directory, regardless of the platform on which the test is
running:

```properties
jimfs.junit.jupiter.tempdir.configuration.default=windows
```

All Jimfs-based temporary directories will be configured accordingly unless `@JimfsTempDir` is used with its `value`
attribute set.
