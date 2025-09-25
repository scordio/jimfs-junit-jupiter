# Usage

## `@TempDir` with `JimfsTempDirFactory`

The simplest way to use Jimfs JUnit Jupiter is to set the
[`factory`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html#factory())
attribute of `@TempDir` to `JimfsTempDirFactory`:

```java
import io.github.scordio.jimfs.junit.jupiter.JimfsTempDirFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@Test
void test(@TempDir(factory = JimfsTempDirFactory.class) Path tempDir) {
  assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
}
```

`tempDir` is resolved to an in-memory temporary directory based on Jimfs, configured appropriately for the current
platform.

While the example shows the injection of the temporary directory into a test method parameter,
the same can also be done with class fields and constructor parameters.

## `@JimfsTempDir`

`@JimfsTempDir` is an annotation that can be used as a drop-in replacement for
`@TempDir(factory = JimfsTempDirFactory.class)`:

```java
import io.github.scordio.jimfs.junit.jupiter.JimfsTempDir;
import org.junit.jupiter.api.Test;

@Test
void test(@JimfsTempDir Path tempDir) {
  assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
}
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

```java
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.WINDOWS;

import io.github.scordio.jimfs.junit.jupiter.JimfsTempDir;
import org.junit.jupiter.api.Test;

@Test
void test(@JimfsTempDir(WINDOWS) Path tempDir) {
  assertThat(tempDir.getFileSystem().getSeparator()).isEqualTo("\\");
}
```
