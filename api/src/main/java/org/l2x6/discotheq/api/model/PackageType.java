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
public interface PackageType extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PackageTypeRecord(String name, String uiString, String apiString) implements PackageType {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof PackageType other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownPackageType implements PackageType {
        JDK("JDK", "JDK", "jdk"),
        JRE("JRE", "JRE", "jre"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final PackageTypeRecord value;

        KnownPackageType(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new PackageTypeRecord(name, uiString, apiString);
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
