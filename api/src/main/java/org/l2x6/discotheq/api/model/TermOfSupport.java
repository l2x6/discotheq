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

@JsonDeserialize(as = TermOfSupport.TermOfSupportRecord.class)
public interface TermOfSupport extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TermOfSupportRecord(String name, String uiString, String apiString) implements TermOfSupport {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof TermOfSupport other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownTermOfSupport implements TermOfSupport {
        STS("STS", "short term stable", "sts"),
        MTS("MTS", "mid term stable", "mts"),
        LTS("LTS", "long term stable", "lts"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final TermOfSupportRecord value;

        KnownTermOfSupport(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new TermOfSupportRecord(name, uiString, apiString);
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
