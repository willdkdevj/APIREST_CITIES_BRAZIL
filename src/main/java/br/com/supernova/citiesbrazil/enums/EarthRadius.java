package br.com.supernova.citiesbrazil.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EarthRadius {
    METER("m", 6371182f),
    KILOMETERS("km", 6371.182f),
    MILES("mi",3963.799824f);

    private String unit;
    private Float value;
}
