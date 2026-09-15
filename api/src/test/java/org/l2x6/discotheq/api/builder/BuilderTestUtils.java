/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import java.time.Duration;
import org.l2x6.discotheq.api.DiscoClient;

public final class BuilderTestUtils {

    public static final Duration TIMEOUT = Duration.ofSeconds(30);
    public static final DiscoClient CLIENT = DiscoClient.of("https://api.foojay.io");

    private BuilderTestUtils() {
    }
}
