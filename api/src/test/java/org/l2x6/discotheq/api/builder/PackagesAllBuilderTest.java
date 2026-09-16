/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;
import org.l2x6.discotheq.api.model.ReleaseStatus.KnownReleaseStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.CLIENT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.TIMEOUT;

@QuarkusTest
class PackagesAllBuilderTest {

    @Test
    void packagesAllWithDefaults() {
        assertValid(call(CLIENT.packagesAll()));
    }

    @Test
    void packagesAllWithOptions() {
        assertDownloadableGaPackages(call(CLIENT.packagesAll()
                .downloadable()
                .notIncludeEa()));

        assertDownloadableGaPackages(call(CLIENT.packagesAll()
                .downloadable(true)
                .includeEa(false)));
    }

    private static ApiResponse<DiscoPackage> call(PackagesAllBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }

    private static void assertDownloadableGaPackages(ApiResponse<DiscoPackage> response) {
        assertValid(response);
        assertThat(response.result()).allSatisfy(pkg -> {
            assertThat(pkg.directlyDownloadable()).isTrue();
            assertThat(pkg.releaseStatus()).isEqualTo(KnownReleaseStatus.GA);
        });
    }

    private static void assertValid(ApiResponse<DiscoPackage> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .hasSizeGreaterThan(1_000)
                .allSatisfy(pkg -> {
                    assertThat(pkg.id()).isNotBlank();
                    assertThat(pkg.archiveType()).isNotNull();
                    assertThat(pkg.archiveType().apiString()).isNotBlank();
                    assertThat(pkg.distribution()).isNotBlank();
                    assertThat(pkg.majorVersion()).isPositive();
                    assertThat(pkg.javaVersion()).isNotBlank();
                    assertThat(pkg.architecture()).isNotNull();
                    assertThat(pkg.architecture().apiString()).isNotBlank();
                    assertThat(pkg.operatingSystem()).isNotNull();
                    assertThat(pkg.operatingSystem().apiString()).isNotBlank();
                    assertThat(pkg.packageType()).isNotNull();
                    assertThat(pkg.packageType().apiString()).isNotBlank();
                    assertThat(pkg.directlyDownloadable()).isNotNull();
                    assertThat(pkg.links()).isNotNull();
                });
    }
}
