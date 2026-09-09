/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DistributionParameters(String discoveryScopeId) {
}
