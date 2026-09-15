/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.Vendor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.CLIENT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.TIMEOUT;

@QuarkusTest
class VendorsBuilderTest {

    @Test
    void vendorsWithDefaults() {
        assertValid(call(CLIENT.vendors()));
    }

    @Test
    void vendorsWithOptions() {
        assertValid(call(CLIENT.vendors().includeDistributions()));
        assertValid(call(CLIENT.vendors().includeDistributions(true)));
        assertValid(call(CLIENT.vendors().notIncludeDistributions()));
    }

    private static ApiResponse<Vendor> call(VendorsBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }

    private static void assertValid(ApiResponse<Vendor> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .hasSizeGreaterThanOrEqualTo(18)
                .allSatisfy(vendor -> {
                    assertThat(vendor.uiString()).isNotBlank();
                    assertThat(vendor.apiString()).isNotBlank();
                });
    }
}
