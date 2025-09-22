package com.hecookin.chemlibmekanized.api;

public interface Chemical {
    String getAbbreviation();
    int getColor();
    String getChemicalName();
    MatterState getMatterState();
    ChemicalType getChemicalType();
}