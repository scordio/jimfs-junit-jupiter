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

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDirFactory;

public final class JimfsTempDirFactory implements TempDirFactory {

  private static final String DEFAULT_PREFIX = "junit-";

  private FileSystem fileSystem;

  /** {@inheritDoc} */
  @Override
  public Path createTempDirectory(
      AnnotatedElementContext elementContext, ExtensionContext extensionContext)
      throws IOException {
    Supplier<Configuration> configuration =
        elementContext
            .findAnnotation(JimfsTempDir.class)
            .map(JimfsTempDir::value)
            .map(JimfsTempDir.Configuration::getConfiguration)
            .orElse(Configuration::forCurrentPlatform);

    fileSystem = Jimfs.newFileSystem(configuration.get());
    Path root = fileSystem.getRootDirectories().iterator().next();
    return Files.createTempDirectory(root, DEFAULT_PREFIX);
  }

  /** {@inheritDoc} */
  @Override
  public void close() throws IOException {
    fileSystem.close();
  }
}
