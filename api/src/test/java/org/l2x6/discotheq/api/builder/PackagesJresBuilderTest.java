/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
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
class PackagesJresBuilderTest {

    @Test
    void packagesJresWithDefaults() {
        final ApiResponse<DiscoPackage> response = call(CLIENT.packagesJres());

        assertValidPackages(response);
        assertThat(response.result()).allSatisfy(pkg -> assertThat(pkg.packageType()).isEqualTo(KnownPackageType.JRE));
    }

    @Test
    void packagesJresWithOptions() {
        final ApiResponse<DiscoPackage> response = call(CLIENT.packagesJres()
                .jdkVersion(21)
                .distribution(List.of("zulu"))
                .releaseStatus(List.of(KnownReleaseStatus.GA))
                .latest(KnownLatest.PER_DISTRIBUTION));

        assertValidPackages(response);
        assertThat(response.result()).allSatisfy(pkg -> {
            assertThat(pkg.jdkVersion()).isEqualTo(21);
            assertThat(pkg.distribution()).isEqualTo("zulu");
            assertThat(pkg.releaseStatus()).isEqualTo(KnownReleaseStatus.GA);
            assertThat(pkg.packageType()).isEqualTo(KnownPackageType.JRE);
        });
    }

    private static ApiResponse<DiscoPackage> call(PackagesJresBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }
}
