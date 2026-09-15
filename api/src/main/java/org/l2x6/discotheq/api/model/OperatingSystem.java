/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@JsonDeserialize(as = OperatingSystem.OperatingSystemRecord.class)
public interface OperatingSystem extends ApiValue {

    String libCType();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record OperatingSystemRecord(
            String name, String uiString, String apiString, @JsonProperty("lib_c_type") String libCType)
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
        ALPINE_LINUX("Alpine Linux", "linux", "musl"),
        LINUX_MUSL("Linux Musl", "linux", "musl"),
        LINUX("Linux", "linux", "glibc"),
        FREE_BSD("FreeBSD", "free_bsd", "libc"),
        MACOS("Mac OS", "macos", "libc"),
        WINDOWS("Windows", "windows", "c_std_lib"),
        SOLARIS("Solaris", "solaris", "libc"),
        QNX("QNX", "qnx", "libc"),
        AIX("AIX", "aix", "libc"),
        NONE("-", "", ""),
        NOT_FOUND("", "", "");

        private final OperatingSystemRecord value;

        KnownOperatingSystem(String uiString, String apiString, String libCType) {
            this.value = new OperatingSystemRecord(name(), uiString, apiString, libCType);
        }

        @Override
        public String uiString() {
            return value.uiString();
        }

        @Override
        public String apiString() {
            return value.apiString();
        }

        @Override
        public String libCType() {
            return value.libCType();
        }
    }
}
