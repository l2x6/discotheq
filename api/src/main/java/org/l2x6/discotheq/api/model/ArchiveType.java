/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@JsonDeserialize(as = ArchiveType.ArchiveTypeRecord.class)
public interface ArchiveType extends ApiValue {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record ArchiveTypeRecord(String name, String uiString, String apiString) implements ArchiveType {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof ArchiveType other
                            && Objects.equals(name, other.name())
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownArchiveType implements ArchiveType {
        APK("APK", "apk", "apk"),
        BIN("BIN", "bin", "bin"),
        CAB("CAB", "cab", "cab"),
        DEB("DEB", "deb", "deb"),
        DMG("DMG", "dmg", "dmg"),
        MSI("MSI", "msi", "msi"),
        PKG("PKG", "pkg", "pkg"),
        RPM("RPM", "rpm", "rpm"),
        SRC_TAR("SRC_TAR", "src.tar.gz", "src_tar"),
        TAR_GZ("TAR_GZ", "tar.gz", "tar.gz"),
        TAR_XZ("TAR_XZ", "tar.xz", "tar.xz"),
        TAP_ZIP("TAP_ZIP", "tap.zip", "tap.zip"),
        TAR("TAR", "tar", "tar"),
        TGZ("TGZ", "tgz", "tgz"),
        TAR_Z("TAR_Z", "tar.Z", "tar.z"),
        ZIP("ZIP", "zip", "zip"),
        EXE("EXE", "exe", "exe"),
        NONE("NONE", "-", ""),
        NOT_FOUND("NOT_FOUND", "", "");

        private final ArchiveTypeRecord value;

        KnownArchiveType(String name, String uiString, String apiString) {
            assert name.equals(name());
            this.value = new ArchiveTypeRecord(name, uiString, apiString);
        }

        @Override
        public String uiString() {
            return value.uiString();
        }

        @Override
        public String apiString() {
            return value.apiString();
        }
    }
}
