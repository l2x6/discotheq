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

@JsonDeserialize(as = Architecture.ArchitectureRecord.class)
public interface Architecture extends ApiValue {

    String bitness();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record ArchitectureRecord(String name, String uiString, String apiString, String bitness) implements Architecture {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof Architecture other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString())
                            && Objects.equals(bitness, other.bitness());
        }
    }

    enum KnownArchitecture implements Architecture {
        AARCH64("AARCH64", "aarch64", "64"),
        AARCH32("AARCH32", "aarch32", "32"),
        ARM64("ARM64", "arm64", "64"),
        ARM32("ARM32", "arm32", "32"),
        ARM("ARM", "arm", "32"),
        ARMHF("ARMHF", "armhf", "32"),
        ARMEL("ARMEL", "armel", "32"),
        MIPS("MIPS", "mips", "32"),
        MIPSEL("MIPS EL", "mipsel", "32"),
        PPC64LE("PPC64LE", "ppc64le", "64"),
        PPC64("PPC64", "ppc64", "64"),
        PPC("Power PC", "ppc", "32"),
        RISCV64("RISCv64", "riscv64", "64"),
        S390X("S390X", "s390x", "64"),
        SPARCV9("Sparc V9", "sparcv9", "64"),
        SPARC("Sparc", "sparc", "32"),
        X64("X64", "x64", "64"),
        X32("X32", "x32", "32"),
        I386("I386", "i386", "32"),
        I586("I586", "i386", "32"),
        I686("I686", "i386", "32"),
        X86_64("X86_64", "x86_64", "64"),
        X86("X86", "x86", "32"),
        AMD64("AMD64", "amd64", "64"),
        IA64("IA-64", "ia64", "64"),
        NONE("-", "", ""),
        NOT_FOUND("", "", "");

        private final ArchitectureRecord value;

        KnownArchitecture(String uiString, String apiString, String bitness) {
            this.value = new ArchitectureRecord(name(), uiString, apiString, bitness);
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
        public String bitness() {
            return value.bitness();
        }
    }
}
