/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoveryScope.KnownDiscoveryScope;
import org.l2x6.discotheq.api.model.MajorVersion;
import org.l2x6.discotheq.api.model.ReleaseStatus.KnownReleaseStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.CLIENT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.TIMEOUT;

@QuarkusTest
class MajorVersionsBuilderTest {

    @Test
    void majorVersionsWithDefaults() {
        final ApiResponse<MajorVersion> response = call(CLIENT.majorVersions());

        assertValid(response);
        assertThat(response.result())
                .hasSizeGreaterThanOrEqualTo(22)
                .allSatisfy(version -> {
                    assertThat(version.earlyAccessOnly()).isFalse();
                    assertThat(version.releaseStatus()).isEqualTo(KnownReleaseStatus.GA);
                    assertThat(version.versions()).isNotEmpty();
                });
    }

    @Test
    void majorVersionsWithExplicitAndScopeOptions() {

        assertMajorVersionsWithExplicitAndScopeOptions(call(CLIENT.majorVersions()
                .notEa()
                .ga()
                .maintained()
                .includeBuild()
                .discoveryScopeId(List.of(KnownDiscoveryScope.PUBLIC))
                .notIncludeVersions()));

        assertMajorVersionsWithExplicitAndScopeOptions(call(CLIENT.majorVersions()
                .ea(false)
                .ga(true)
                .maintained(true)
                .includeBuild(true)
                .discoveryScopeId(List.of(KnownDiscoveryScope.PUBLIC))
                .matchAll()
                .includeVersions(false)));
    }

    private static ApiResponse<MajorVersion> call(MajorVersionsBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }

    private static void assertMajorVersionsWithExplicitAndScopeOptions(ApiResponse<MajorVersion> response) {
        assertValid(response);
        assertThat(response.result())
                .hasSize(7)
                .allSatisfy(version -> {
                    assertThat(version.earlyAccessOnly()).isFalse();
                    assertThat(version.releaseStatus()).isEqualTo(KnownReleaseStatus.GA);
                    assertThat(version.maintained()).isTrue();
                    assertThat(version.versions()).isNullOrEmpty();
                });
    }

    private static void assertValid(ApiResponse<MajorVersion> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .allSatisfy(version -> {
                    assertThat(version.majorVersion()).isPositive();
                    assertThat(version.termOfSupport()).isNotNull();
                    assertThat(version.termOfSupport().apiString()).isNotBlank();
                    assertThat(version.maintained()).isNotNull();
                    assertThat(version.earlyAccessOnly()).isNotNull();
                    assertThat(version.releaseStatus()).isNotNull();
                    assertThat(version.releaseStatus().apiString()).isNotBlank();
                });
    }
}
