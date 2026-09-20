/*
 * Copyright © 2024-present Stefano Cordio
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

import com.google.common.jimfs.Jimfs;
import io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDirFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

/**
 * {@link TempDirFactory} implementation that creates an in-memory temporary directory via
 * {@link Jimfs}, using {@value DEFAULT_PREFIX} as the name prefix.
 *
 * <p>
 * When used as a standalone factory within the {@link org.junit.jupiter.api.io.TempDir}
 * annotation, or set as value for the
 * {@value org.junit.jupiter.api.io.TempDir#DEFAULT_FACTORY_PROPERTY_NAME} configuration
 * parameter, the factory configures the underlying file system appropriately for the
 * {@link com.google.common.jimfs.Configuration#forCurrentPlatform() current platform}.
 *
 * <p>
 * For better control over the underlying in-memory file system, consider using one of the
 * following options:
 *
 * <ul>
 * <li>The {@link JimfsTempDir} composed annotation and its {@link JimfsTempDir#value()
 * value} attribute.
 * <li>The {@value JimfsTempDir#DEFAULT_CONFIGURATION_PARAMETER_NAME} configuration
 * parameter.
 * </ul>
 *
 * <p>
 * Please note that only annotated fields or parameters of type {@link Path} are supported
 * as Jimfs is a non-default file system, and {@link java.io.File} instances are
 * associated with the default file system only.
 *
 * @see Jimfs#newFileSystem(com.google.common.jimfs.Configuration)
 * @see com.google.common.jimfs.Configuration#forCurrentPlatform()
 */
@SuppressWarnings("exports")
public final class JimfsTempDirFactory implements TempDirFactory {

	private static final String DEFAULT_PREFIX = "junit-";

	private @Nullable FileSystem fileSystem;

	/**
	 * Create a new {@code JimfsTempDirFactory} instance.
	 */
	public JimfsTempDirFactory() {
	}

	@Override
	public Path createTempDirectory(AnnotatedElementContext elementContext, ExtensionContext extensionContext)
			throws IOException {
		Configuration configuration = getFromAnnotation(elementContext)
			.or(() -> getFromConfigurationParameter(extensionContext))
			.orElseGet(JimfsTempDirFactory::getDefault);

		fileSystem = Jimfs.newFileSystem(switch (configuration) {
			case FOR_CURRENT_PLATFORM -> com.google.common.jimfs.Configuration.forCurrentPlatform();
			case OS_X -> com.google.common.jimfs.Configuration.osX();
			case UNIX -> com.google.common.jimfs.Configuration.unix();
			case WINDOWS -> com.google.common.jimfs.Configuration.windows();
			default -> throw new RuntimeException("Should not be thrown");
		});

		Path root = fileSystem.getRootDirectories().iterator().next();
		return Files.createTempDirectory(root, DEFAULT_PREFIX);
	}

	private static Optional<Configuration> getFromAnnotation(AnnotatedElementContext elementContext) {
		return elementContext.findAnnotation(JimfsTempDir.class)
			.map(JimfsTempDir::value)
			.filter(configuration -> configuration != Configuration.DEFAULT);
	}

	private static Optional<Configuration> getFromConfigurationParameter(ExtensionContext extensionContext) {
		return extensionContext
			.getConfigurationParameter(JimfsTempDir.DEFAULT_CONFIGURATION_PARAMETER_NAME,
					JimfsTempDirFactory::transform)
			.filter(configuration -> configuration != Configuration.DEFAULT);
	}

	private static JimfsTempDir.Configuration getDefault() {
		return transform(JimfsTempDir.DEFAULT_CONFIGURATION_DEFAULT);
	}

	private static Configuration transform(String value) {
		return Configuration.valueOf(value.strip().toUpperCase(Locale.ROOT));
	}

	@Override
	public void close() throws IOException {
		if (fileSystem != null) {
			fileSystem.close();
			fileSystem = null;
		}
	}

}
