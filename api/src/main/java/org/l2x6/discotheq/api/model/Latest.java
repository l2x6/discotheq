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
import org.l2x6.discotheq.api.jackson.ApiValueDeserializer;

@JsonDeserialize(using = ApiValueDeserializer.class)
public interface Latest extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record LatestRecord(String name, String uiString, String apiString) implements Latest {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof Latest other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownLatest implements Latest {
        ALL_OF_VERSION("ALL_OF_VERSION", "all of version", "all_of_version"),
        OVERALL("OVERALL", "overall", "overall"),
        PER_DISTRIBUTION("PER_DISTRIBUTION", "per distribution", "per_distro"),
        PER_VERSION("PER_VERSION", "per version", "per_version"),
        AVAILABLE("AVAILABLE", "available", "available"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final LatestRecord value;

        KnownLatest(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new LatestRecord(name, uiString, apiString);
        }

        @Override
        public String uiString() {
            return value.uiString();
        }

        @Override
        @JsonValue
        public String apiString() {
            return value.apiString();
        }

        @Override
        public String toString() {
            return apiString();
        }
    }
}
