package com.capstone.producer.shared.bindings;

import com.capstone.producer.shared.enums.NoFlyZoneType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseNoFlyZone {
    protected float altitude;

    @NotEmpty
    protected String notamNumber;

    protected String description;

    protected Date CreatedAt;

    protected NoFlyZoneType type;

    public String getId() {
        return "notam-" + notamNumber;
    }
}
