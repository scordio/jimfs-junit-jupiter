/*
 * Copyright © 2024 Stefano Cordio (stefano.cordio@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.scordio.jimfs.junit.jupiter;

import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.FOR_CURRENT_PLATFORM;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.OS_X;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.UNIX;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.WINDOWS;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.osXFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.unixFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.windowsFileSystem;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

class JimfsTempDirTests {

  @Nested
  class With_Default_Configuration {

    @JimfsTempDir Path tempDir;

    @EnabledOnOs(OS.MAC)
    @Test
    void should_use_os_x_configuration() {
      assertThat(tempDir).satisfies(osXFileSystem());
    }

    @DisabledOnOs({OS.MAC, OS.WINDOWS})
    @Test
    void should_use_unix_configuration() {
      assertThat(tempDir).satisfies(unixFileSystem());
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void should_use_windows_configuration() {
      assertThat(tempDir).satisfies(windowsFileSystem());
    }
  }

  @Nested
  class With_FOR_CURRENT_PLATFORM_Configuration {

    @JimfsTempDir(FOR_CURRENT_PLATFORM)
    Path tempDir;

    @EnabledOnOs(OS.MAC)
    @Test
    void should_use_os_x_configuration() {
      assertThat(tempDir).satisfies(osXFileSystem());
    }

    @DisabledOnOs({OS.MAC, OS.WINDOWS})
    @Test
    void should_use_unix_configuration() {
      assertThat(tempDir).satisfies(unixFileSystem());
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void should_use_windows_configuration() {
      assertThat(tempDir).satisfies(windowsFileSystem());
    }
  }

  @Nested
  class With_OS_X_Configuration {

    @Test
    void should_use_os_x_configuration(@JimfsTempDir(OS_X) Path tempDir) {
      assertThat(tempDir).satisfies(osXFileSystem());
    }
  }

  @Nested
  class With_UNIX_Configuration {

    @Test
    void should_use_unix_configuration(@JimfsTempDir(UNIX) Path tempDir) {
      assertThat(tempDir).satisfies(unixFileSystem());
    }
  }

  @Nested
  class With_WINDOWS_Configuration {

    @Test
    void should_use_windows_configuration(@JimfsTempDir(WINDOWS) Path tempDir) {
      assertThat(tempDir).satisfies(windowsFileSystem());
    }
  }
}
