package com.qlda.nhom6;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN(1),
    USER(2);

    public final long value;

    // Constructor to assign value
    Role(long value) {
        this.value = value;
    }
}
