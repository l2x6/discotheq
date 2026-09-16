/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;
import org.l2x6.discotheq.api.model.Latest.KnownLatest;
import org.l2x6.discotheq.api.model.PackageType.KnownPackageType;
import org.l2x6.discotheq.api.model.ReleaseStatus.KnownReleaseStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.CLIENT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.TIMEOUT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.assertValidPackages;

@QuarkusTest
class PackagesJdksBuilderTest {

    @Test
    void packagesJdksWithDefaults() {
        final ApiResponse<DiscoPackage> response = call(CLIENT.packagesJdks());

        assertValidPackages(response);
        assertThat(response.result()).allSatisfy(pkg -> assertThat(pkg.packageType()).isEqualTo(KnownPackageType.JDK));
    }

    @Test
    void packagesJdksWithOptions() {
        final ApiResponse<DiscoPackage> response = call(CLIENT.packagesJdks()
                .jdkVersion(21)
                .distribution("temurin")
                .releaseStatus(KnownReleaseStatus.GA)
                .latest(KnownLatest.PER_DISTRIBUTION));

        assertValidPackages(response);
        assertThat(response.result()).allSatisfy(pkg -> {
            assertThat(pkg.jdkVersion()).isEqualTo(21);
            assertThat(pkg.distribution()).isEqualTo("temurin");
            assertThat(pkg.releaseStatus()).isEqualTo(KnownReleaseStatus.GA);
            assertThat(pkg.packageType()).isEqualTo(KnownPackageType.JDK);
        });
    }

    private static ApiResponse<DiscoPackage> call(PackagesJdksBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }
}
