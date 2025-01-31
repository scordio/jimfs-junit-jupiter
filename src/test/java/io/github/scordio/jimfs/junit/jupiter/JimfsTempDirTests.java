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

import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.DEFAULT;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.FOR_CURRENT_PLATFORM;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.OS_X;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.UNIX;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.Configuration.WINDOWS;
import static io.github.scordio.jimfs.junit.jupiter.JimfsTempDir.DEFAULT_CONFIGURATION_PARAMETER_NAME;
import static io.github.scordio.jimfs.junit.jupiter.JupiterEngineTestKit.executeTests;
import static io.github.scordio.jimfs.junit.jupiter.JupiterEngineTestKit.executeTestsForClass;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.osXFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.unixFileSystem;
import static io.github.scordio.jimfs.junit.jupiter.Requirements.windowsFileSystem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.testkit.engine.EngineExecutionResults;

@DisplayName("JimfsTempDir")
class JimfsTempDirTests {

  @Nested
  @DisplayName("without configuration parameters")
  class without_config_parameters {

    @Nested
    class with_DEFAULT_configuration {

      @EnabledOnOs(MAC)
      @Test
      void should_apply_OS_X_configuration_on_Mac_platform() {
        executeTestsForClass(OsXTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class OsXTestCase {

        @Test
        void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
          assertThat(tempDir1).satisfies(osXFileSystem());
          assertThat(tempDir2).satisfies(osXFileSystem());
        }
      }

      @DisabledOnOs({MAC, OS.WINDOWS})
      @Test
      void should_apply_UNIX_configuration_on_Unix_platform() {
        executeTestsForClass(UnixTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class UnixTestCase {

        @Test
        void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
          assertThat(tempDir1).satisfies(unixFileSystem());
          assertThat(tempDir2).satisfies(unixFileSystem());
        }
      }

      @EnabledOnOs(OS.WINDOWS)
      @Test
      void should_apply_WINDOWS_configuration_on_Windows_platform() {
        executeTestsForClass(WindowsTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class WindowsTestCase {

        @Test
        void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
          assertThat(tempDir1).satisfies(windowsFileSystem());
          assertThat(tempDir2).satisfies(windowsFileSystem());
        }
      }
    }

    @Nested
    class with_FOR_CURRENT_PLATFORM_configuration {

      @EnabledOnOs(MAC)
      @Test
      void should_apply_OS_X_configuration_on_Mac_platform() {
        executeTestsForClass(OsXTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class OsXTestCase {

        @Test
        void test(@JimfsTempDir(FOR_CURRENT_PLATFORM) Path tempDir) {
          assertThat(tempDir).satisfies(osXFileSystem());
        }
      }

      @DisabledOnOs({MAC, OS.WINDOWS})
      @Test
      void should_apply_UNIX_configuration_on_Unix_platform() {
        executeTestsForClass(UnixTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class UnixTestCase {

        @Test
        void test(@JimfsTempDir(FOR_CURRENT_PLATFORM) Path tempDir) {
          assertThat(tempDir).satisfies(unixFileSystem());
        }
      }

      @EnabledOnOs(OS.WINDOWS)
      @Test
      void should_apply_WINDOWS_configuration_on_Windows_platform() {
        executeTestsForClass(WindowsTestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class WindowsTestCase {

        @Test
        void test(@JimfsTempDir(FOR_CURRENT_PLATFORM) Path tempDir) {
          assertThat(tempDir).satisfies(windowsFileSystem());
        }
      }
    }

    @Nested
    class with_OS_X_configuration {

      @Test
      void should_apply_OS_X_configuration() {
        executeTestsForClass(TestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class TestCase {

        @Test
        void test(@JimfsTempDir(OS_X) Path tempDir) {
          assertThat(tempDir).satisfies(osXFileSystem());
        }
      }
    }

    @Nested
    class with_UNIX_configuration {

      @Test
      void should_apply_UNIX_configuration() {
        executeTestsForClass(TestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class TestCase {

        @Test
        void test(@JimfsTempDir(UNIX) Path tempDir) {
          assertThat(tempDir).satisfies(unixFileSystem());
        }
      }
    }

    @Nested
    class with_WINDOWS_configuration {

      @Test
      void should_apply_WINDOWS_configuration_with_WINDOWS_value() {
        executeTestsForClass(TestCase.class)
            .testEvents()
            .assertStatistics(stats -> stats.started(1).succeeded(1));
      }

      static class TestCase {

        @Test
        void test(@JimfsTempDir(WINDOWS) Path tempDir) {
          assertThat(tempDir).satisfies(windowsFileSystem());
        }
      }
    }
  }

  @Nested
  @DisplayName("with Jimfs default configuration (configuration parameter)")
  class with_Jimfs_default_configuration_config_parameter {

    private static EngineExecutionResults executeTestsForClass(
        Class<?> testClass, String configuration) {
      return executeTests(
          request()
              .selectors(selectClass(testClass))
              .configurationParameter(DEFAULT_CONFIGURATION_PARAMETER_NAME, configuration)
              .build());
    }

    @EnabledOnOs(MAC)
    @ParameterizedTest
    @ValueSource(strings = {"DEFAULT", "default"})
    void should_apply_OS_X_configuration_with_DEFAULT_parameter_on_Mac_platform(
        String configuration) {
      executeTestsForClass(OsXTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @EnabledOnOs(MAC)
    @ParameterizedTest
    @ValueSource(strings = {"FOR_CURRENT_PLATFORM", "for_current_platform"})
    void should_apply_OS_X_configuration_with_FOR_CURRENT_PLATFORM_parameter_on_Mac_platform(
        String configuration) {
      executeTestsForClass(OsXTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"OS_X", "os_x"})
    void should_apply_OS_X_configuration_with_OS_X_parameter(String configuration) {
      executeTestsForClass(OsXTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    static class OsXTestCase {

      @Test
      void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
        assertThat(tempDir1).satisfies(osXFileSystem());
        assertThat(tempDir2).satisfies(osXFileSystem());
      }
    }

    @DisabledOnOs({MAC, OS.WINDOWS})
    @ParameterizedTest
    @ValueSource(strings = {"DEFAULT", "default"})
    void should_apply_UNIX_configuration_with_DEFAULT_parameter_on_Unix_platform(
        String configuration) {
      executeTestsForClass(UnixTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @DisabledOnOs({MAC, OS.WINDOWS})
    @ParameterizedTest
    @ValueSource(strings = {"FOR_CURRENT_PLATFORM", "for_current_platform"})
    void should_apply_UNIX_configuration_with_FOR_CURRENT_PLATFORM_parameter_on_Unix_platform(
        String configuration) {
      executeTestsForClass(UnixTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UNIX", "unix"})
    void should_apply_UNIX_configuration_with_UNIX_parameter(String configuration) {
      executeTestsForClass(UnixTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    static class UnixTestCase {

      @Test
      void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
        assertThat(tempDir1).satisfies(unixFileSystem());
        assertThat(tempDir2).satisfies(unixFileSystem());
      }
    }

    @EnabledOnOs(OS.WINDOWS)
    @ParameterizedTest
    @ValueSource(strings = {"DEFAULT", "default"})
    void should_apply_WINDOWS_configuration_with_DEFAULT_parameter_on_Windows_platform(
        String configuration) {
      executeTestsForClass(WindowsTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @EnabledOnOs(OS.WINDOWS)
    @ParameterizedTest
    @ValueSource(strings = {"FOR_CURRENT_PLATFORM", "for_current_platform"})
    void should_apply_WINDOWS_configuration_with_FOR_CURRENT_PLATFORM_parameter_on_Windows_platform(
        String configuration) {
      executeTestsForClass(WindowsTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"WINDOWS", "windows"})
    void should_apply_WINDOWS_configuration_with_WINDOWS_parameter(String configuration) {
      executeTestsForClass(WindowsTestCase.class, configuration)
          .testEvents()
          .assertStatistics(stats -> stats.started(1).succeeded(1));
    }

    static class WindowsTestCase {

      @Test
      void test(@JimfsTempDir Path tempDir1, @JimfsTempDir(DEFAULT) Path tempDir2) {
        assertThat(tempDir1).satisfies(windowsFileSystem());
        assertThat(tempDir2).satisfies(windowsFileSystem());
      }
    }
  }
}
