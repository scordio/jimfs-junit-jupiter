/*
 * Copyright © 2024-2025 Stefano Cordio
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
package io.github.scordio;

// --8<-- [start:import]
import io.github.scordio.jimfs.junit.jupiter.JimfsTempDir;
// --8<-- [end:import]

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

// --8<-- [start:import-windows]

import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.WINDOWS;
// --8<-- [end:import-windows]
import static org.junit.jupiter.api.Assertions.assertEquals;

class JimfsTempDirDemo {

	static class Default {

// @formatter:off
// --8<-- [start:test-default]

@Test
void test(@JimfsTempDir Path tempDir) {
	assertEquals("jimfs", tempDir.getFileSystem().provider().getScheme());
}
// --8<-- [end:test-default]
// @formatter:on

	}

	static class Windows {

// @formatter:off
// --8<-- [start:test-windows]

@Test
void test(@JimfsTempDir(WINDOWS) Path tempDir) {
	assertEquals("\\", tempDir.getFileSystem().getSeparator());
}
// --8<-- [end:test-windows]
// @formatter:on

	}

}
