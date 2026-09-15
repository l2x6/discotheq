/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@JsonDeserialize(as = Vendor.VendorRecord.class)
public interface Vendor {

    String uiString();

    String apiString();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record VendorRecord(String uiString, String apiString) implements Vendor {
        @Override
        public boolean equals(Object obj) {
            return this == obj
                    || obj instanceof Vendor other
                            && Objects.equals(uiString, other.uiString())
                            && Objects.equals(apiString, other.apiString());
        }
    }

    enum KnownVendor implements Vendor {
        adopt_open_jdk("AdoptOpenJDK"),
        alibaba("Alibaba"),
        amazon("Amazon"),
        asymm("Asymm Systems"),
        azul("Azul"),
        bell_soft("BellSoft"),
        community("Community"),
        eclipse_foundation("Eclipse Foundation"),
        gluon("Gluon"),
        huawei("Huawei"),
        ibm("IBM"),
        jetbrains("JetBrains"),
        microsoft("Microsoft"),
        oracle("Oracle"),
        open_logic("OpenLogic"),
        red_hat("Red Hat"),
        sap("SAP"),
        tencent("Tencent");

        private final String uiString;

        KnownVendor(String uiString) {
            this.uiString = uiString;
        }

        @Override
        public String uiString() {
            return uiString;
        }

        @Override
        public String apiString() {
            return name();
        }

    }
}
