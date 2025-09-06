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

import org.junit.jupiter.api.io.TempDir;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * {@link TempDir} composed annotation that sets the {@link TempDir#factory() factory}
 * attribute to {@link JimfsTempDirFactory}.
 *
 * <p>
 * The annotation can be used as a drop-in replacement for {@code @TempDir(factory =
 * JimfsTempDirFactory.class)}.
 *
 * <p>
 * Please note that only annotated fields or parameters of type {@link java.nio.file.Path}
 * are supported as Jimfs is a non-default file system, and {@link java.io.File} instances
 * are associated with the default file system only.
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TempDir(factory = JimfsTempDirFactory.class)
@SuppressWarnings("exports")
public @interface JimfsTempDir {

	/**
	 * Configuration parameter to set the default {@link Configuration Configuration} for
	 * the in-memory file system.
	 *
	 * <p>
	 * If this configuration parameter is not set, the default is
	 * {@link Configuration#FOR_CURRENT_PLATFORM}.
	 *
	 * @since 0.2.0
	 */
	String DEFAULT_CONFIGURATION_PARAMETER_NAME = "jimfs.junit.jupiter.tempdir.configuration.default";

	/**
	 * Configuration for the in-memory file system.
	 *
	 * <p>
	 * Defaults to {@link Configuration#FOR_CURRENT_PLATFORM}.
	 * @return the configuration to use for the in-memory file system
	 */
	Configuration value() default Configuration.DEFAULT;

	/**
	 * Enumeration of configurations for the in-memory file system.
	 *
	 * @see com.google.common.jimfs.Configuration
	 */
	enum Configuration {

		/**
		 * Default configuration.
		 *
		 * @see #DEFAULT_CONFIGURATION_PARAMETER_NAME
		 * @since 0.2.0
		 */
		DEFAULT(com.google.common.jimfs.Configuration::forCurrentPlatform),
		/**
		 * Configuration appropriate to the current platform.
		 *
		 * @see com.google.common.jimfs.Configuration#forCurrentPlatform()
		 */
		FOR_CURRENT_PLATFORM(com.google.common.jimfs.Configuration::forCurrentPlatform),
		/**
		 * Configuration for a Mac OS X-like file system.
		 *
		 * @see com.google.common.jimfs.Configuration#osX()
		 */
		OS_X(com.google.common.jimfs.Configuration::osX),
		/**
		 * Configuration for a Unix-like file system.
		 *
		 * @see com.google.common.jimfs.Configuration#unix()
		 */
		UNIX(com.google.common.jimfs.Configuration::unix),
		/**
		 * Configuration for a Windows-like file system.
		 *
		 * @see com.google.common.jimfs.Configuration#windows()
		 */
		WINDOWS(com.google.common.jimfs.Configuration::windows);

		private final Supplier<com.google.common.jimfs.Configuration> configuration;

		Configuration(Supplier<com.google.common.jimfs.Configuration> configuration) {
			this.configuration = configuration;
		}

		Supplier<com.google.common.jimfs.Configuration> getConfiguration() {
			return configuration;
		}

	}

}
