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

@JsonDeserialize(as = LibCType.LibCTypeRecord.class)
public interface LibCType extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record LibCTypeRecord(String name, String uiString, String apiString) implements LibCType {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof LibCType other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownLibCType implements LibCType {
        GLIBC("GLIBC", "glibc", "glibc"),
        MUSL("MUSL", "musl", "musl"),
        LIBC("LIBC", "libc", "libc"),
        C_STD_LIB("C_STD_LIB", "c std. lib", "c_std_lib"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final LibCTypeRecord value;

        KnownLibCType(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new LibCTypeRecord(name, uiString, apiString);
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
