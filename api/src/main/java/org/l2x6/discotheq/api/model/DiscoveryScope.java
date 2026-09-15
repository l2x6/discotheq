/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@JsonDeserialize(as = DiscoveryScope.DiscoveryScopeRecord.class)
public interface DiscoveryScope {

    String discoveryScopeId();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record DiscoveryScopeRecord(String discoveryScopeId) implements DiscoveryScope {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof DiscoveryScope other
                            && Objects.equals(discoveryScopeId, other.discoveryScopeId());
        }
    }

    enum KnownDiscoveryScope implements DiscoveryScope {
        PUBLIC("public"),
        BUILD_OF_OPENJDK("build_of_openjdk"),
        DIRECTLY_DOWNLOADABLE("directly_downloadable"),
        NOT_DIRECTLY_DOWNLOADABLE("not_directly_downloadable");

        private final DiscoveryScopeRecord value;

        KnownDiscoveryScope(String discoveryScopeId) {
            this.value = new DiscoveryScopeRecord(discoveryScopeId);
        }

        @Override
        @JsonValue
        public String discoveryScopeId() {
            return value.discoveryScopeId();
        }

        @Override
        public String toString() {
            return discoveryScopeId();
        }
    }
}
