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
public interface Fpu extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record FpuRecord(String name, String uiString, String apiString) implements Fpu {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof Fpu other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownFpu implements Fpu {
        HARD_FLOAT("HARD_FLOAT", "hardfloat", "hard_float"),
        SOFT_FLOAT("SOFT_FLOAT", "softfloat", "soft_float"),
        UNKNOWN("UNKNOWN", "unknown", "unknown"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final FpuRecord value;

        KnownFpu(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new FpuRecord(name, uiString, apiString);
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
