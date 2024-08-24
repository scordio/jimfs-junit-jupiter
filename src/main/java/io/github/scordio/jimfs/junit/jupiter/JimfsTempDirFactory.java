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
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDirFactory;

/**
 * {@link TempDirFactory} implementation that creates an in-memory temporary directory via {@link
 * Jimfs}, using {@value DEFAULT_PREFIX} as the name prefix.
 *
 * <p>If used as a standalone factory within the {@link org.junit.jupiter.api.io.TempDir}
 * annotation, or set as value for the {@value DEFAULT_FACTORY_PROPERTY_NAME} configuration
 * property, the factory configures the underlying file system {@link
 * Configuration#forCurrentPlatform() appropriately for the current operating system}.
 *
 * <p>For better control over the underlying in-memory file system, consider using the {@link
 * JimfsTempDir} composed annotation and its {@link JimfsTempDir#value() value} attribute.
 *
 * <p>Please note that only annotated fields or parameters of type {@link java.nio.file.Path} are
 * supported as Jimfs is a non-default file system, and {@link java.io.File} instances are
 * associated with the default file system only.
 *
 * @see Jimfs#newFileSystem(Configuration)
 * @see Configuration#forCurrentPlatform()
 */
public final class JimfsTempDirFactory implements TempDirFactory {

  private static final String DEFAULT_PREFIX = "junit-";

  @SuppressWarnings("unused")
  private static final String DEFAULT_FACTORY_PROPERTY_NAME =
      org.junit.jupiter.api.io.TempDir.DEFAULT_FACTORY_PROPERTY_NAME;

  private FileSystem fileSystem;

  /** Create a new {@code JimfsTempDirFactory} instance. */
  public JimfsTempDirFactory() {}

  /** {@inheritDoc} */
  @Override
  public Path createTempDirectory(
      AnnotatedElementContext elementContext, ExtensionContext extensionContext)
      throws IOException {
    Optional<JimfsTempDir> annotation = elementContext.findAnnotation(JimfsTempDir.class);

    Supplier<Configuration> configuration =
        annotation
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
