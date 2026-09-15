/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Match {
    ANY("any"),
    ALL("all");

    private final String apiString;

    Match(String apiString) {
        this.apiString = apiString;
    }

    @JsonValue
    public String apiString() {
        return apiString;
    }

    @Override
    public String toString() {
        return apiString;
    }
}
