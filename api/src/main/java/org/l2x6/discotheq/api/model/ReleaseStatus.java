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
public interface ReleaseStatus extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record ReleaseStatusRecord(String name, String uiString, String apiString) implements ReleaseStatus {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof ReleaseStatus other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownReleaseStatus implements ReleaseStatus {
        GA("GA", "General Access", "ga"),
        EA("EA", "Early Access", "ea"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final ReleaseStatusRecord value;

        KnownReleaseStatus(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new ReleaseStatusRecord(name, uiString, apiString);
        }

        public static KnownReleaseStatus fromApiString(String value) {
            for (KnownReleaseStatus known : values()) {
                if (known.apiString().equalsIgnoreCase(value)) {
                    return known;
                }
            }
            return null;
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
