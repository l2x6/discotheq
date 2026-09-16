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
public interface Architecture extends ApiValue {

    Bitness bitness();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record ArchitectureRecord(String name, String uiString, String apiString, Bitness bitness) implements Architecture {
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
        AARCH64("AARCH64", "aarch64", Bitness._64),
        AARCH32("AARCH32", "aarch32", Bitness._32),
        ARM64("ARM64", "arm64", Bitness._64),
        ARM32("ARM32", "arm32", Bitness._32),
        ARM("ARM", "arm", Bitness._32),
        ARMHF("ARMHF", "armhf", Bitness._32),
        ARMEL("ARMEL", "armel", Bitness._32),
        MIPS("MIPS", "mips", Bitness._32),
        MIPSEL("MIPS EL", "mipsel", Bitness._32),
        PPC64LE("PPC64LE", "ppc64le", Bitness._64),
        PPC64("PPC64", "ppc64", Bitness._64),
        PPC("Power PC", "ppc", Bitness._32),
        RISCV64("RISCv64", "riscv64", Bitness._64),
        S390X("S390X", "s390x", Bitness._64),
        SPARCV9("Sparc V9", "sparcv9", Bitness._64),
        SPARC("Sparc", "sparc", Bitness._32),
        X64("X64", "x64", Bitness._64),
        X32("X32", "x32", Bitness._32),
        I386("I386", "i386", Bitness._32),
        I586("I586", "i386", Bitness._32),
        I686("I686", "i386", Bitness._32),
        X86_64("X86_64", "x86_64", Bitness._64),
        X86("X86", "x86", Bitness._32),
        AMD64("AMD64", "amd64", Bitness._64),
        IA64("IA-64", "ia64", Bitness._64),
        NONE("-", "", null),
        NOT_FOUND("", "", null);

        private final ArchitectureRecord value;

        KnownArchitecture(String uiString, String apiString, Bitness bitness) {
            this.value = new ArchitectureRecord(name(), uiString, apiString, bitness);
        }

        public static KnownArchitecture fromApiString(String value) {
            for (KnownArchitecture known : values()) {
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
        public Bitness bitness() {
            return value.bitness();
        }
    }
}
