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

import static io.github.scordio.jimfs.junit.jupiter.Requirements.osXFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.unixFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.windowsFileSystem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.io.TempDir;

class JimfsTempDirFactoryTests {

  @TempDir(factory = JimfsTempDirFactory.class)
  Path tempDir;

  @EnabledOnOs(MAC)
  @Test
  void should_use_os_x_configuration() {
    assertThat(tempDir).satisfies(osXFileSystem());
  }

  @DisabledOnOs({MAC, WINDOWS})
  @Test
  void should_use_unix_configuration() {
    assertThat(tempDir).satisfies(unixFileSystem());
  }

  @EnabledOnOs(WINDOWS)
  @Test
  void should_use_windows_configuration() {
    assertThat(tempDir).satisfies(windowsFileSystem());
  }
}
