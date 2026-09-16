/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;
import org.l2x6.discotheq.api.jackson.ApiValueDeserializer;

@JsonDeserialize(using = ApiValueDeserializer.class)
public interface OperatingSystem extends ApiValue {

    LibCType libCType();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record OperatingSystemRecord(
            String name, String uiString, String apiString, @JsonProperty("lib_c_type") LibCType libCType)
            implements
                OperatingSystem {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof OperatingSystem other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString())
                            && Objects.equals(libCType, other.libCType());
        }
    }

    enum KnownOperatingSystem implements OperatingSystem {
        ALPINE_LINUX("Alpine Linux", "linux", LibCType.KnownLibCType.MUSL),
        LINUX_MUSL("Linux Musl", "linux", LibCType.KnownLibCType.MUSL),
        LINUX("Linux", "linux", LibCType.KnownLibCType.GLIBC),
        FREE_BSD("FreeBSD", "free_bsd", LibCType.KnownLibCType.LIBC),
        MACOS("Mac OS", "macos", LibCType.KnownLibCType.LIBC),
        WINDOWS("Windows", "windows", LibCType.KnownLibCType.C_STD_LIB),
        SOLARIS("Solaris", "solaris", LibCType.KnownLibCType.LIBC),
        QNX("QNX", "qnx", LibCType.KnownLibCType.LIBC),
        AIX("AIX", "aix", LibCType.KnownLibCType.LIBC),
        NONE("-", "", LibCType.KnownLibCType.NONE),
        NOT_FOUND("", "", LibCType.KnownLibCType.NOT_FOUND);

        private final OperatingSystemRecord value;

        KnownOperatingSystem(String uiString, String apiString, LibCType libCType) {
            this.value = new OperatingSystemRecord(name(), uiString, apiString, libCType);
        }

        public static KnownOperatingSystem fromApiString(String value) {
            for (KnownOperatingSystem known : values()) {
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

        @Override
        public LibCType libCType() {
            return value.libCType();
        }
    }
}
