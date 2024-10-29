package com.qlda.nhom6;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Provider {
    LOCAL("Local"),
    GOOGLE("Google");

    public final String value;

    // Constructor to assign value
    Provider(String value) {
        this.value = value;
    }
}