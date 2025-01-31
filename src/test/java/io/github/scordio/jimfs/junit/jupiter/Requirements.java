/*
 * Copyright © 2025 Stefano Cordio
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

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.function.Consumer;

class Requirements {

  static Consumer<Path> osXFileSystem() {
    return tempDir -> {
      assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
      assertThat(tempDir.getFileSystem().getSeparator()).isEqualTo("/");
      assertThat(tempDir.getFileSystem().getRootDirectories())
          .map(Path::toString)
          .containsExactly("/");
    };
  }

  static Consumer<Path> unixFileSystem() {
    return tempDir -> {
      assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
      assertThat(tempDir.getFileSystem().getSeparator()).isEqualTo("/");
      assertThat(tempDir.getFileSystem().getRootDirectories())
          .map(Path::toString)
          .containsExactly("/");
    };
  }

  static Consumer<Path> windowsFileSystem() {
    return tempDir -> {
      assertThat(tempDir.getFileSystem().provider().getScheme()).isEqualTo("jimfs");
      assertThat(tempDir.getFileSystem().getSeparator()).isEqualTo("\\");
      assertThat(tempDir.getFileSystem().getRootDirectories())
          .map(Path::toString)
          .containsExactly("C:\\");
    };
  }
}
