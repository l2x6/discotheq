/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import java.time.Duration;
import org.l2x6.discotheq.api.DiscoClient;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;

import static org.assertj.core.api.Assertions.assertThat;

public final class BuilderTestUtils {

    public static final Duration TIMEOUT = Duration.ofSeconds(30);
    public static final DiscoClient CLIENT = DiscoClient.of("https://api.foojay.io");

    private BuilderTestUtils() {
    }

    static void assertValidPackages(ApiResponse<DiscoPackage> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .isNotEmpty()
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
