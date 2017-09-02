package com.gnopai.contactgen.model;

import java.util.HashMap;
import java.util.Map;

public enum State {
    ALASKA("AK", "Alaska"),
    ALABAMA("AL", "Alabama"),
    ARKANSAS("AR", "Arkansas"),
    ARIZONA("AZ", "Arizona"),
    CALIFORNIA("CA", "California"),
    COLORADO("CO", "Colorado"),
    CONNECTICUT("CT", "Connecticut"),
    DISTRICT_OF_COLUMBIA("DC", "District of Columbia"),
    DELAWARE("DE", "Delaware"),
    FLORIDA("FL", "Florida"),
    GEORGIA("GA", "Georgia"),
    GUAM("GU", "Guam"),
    HAWAII("HI", "Hawaii"),
    IOWA("IA", "Iowa"),
    IDAHO("ID", "Idaho"),
    ILLINOIS("IL", "Illinois"),
    INDIANA("IN", "Indiana"),
    KANSAS("KS", "Kansas"),
    KENTUCKY("KY", "Kentucky"),
    LOUISIANA("LA", "Louisiana"),
    MASSACHUSETTS("MA", "Massachusetts"),
    MARYLAND("MD", "Maryland"),
    MAINE("ME", "Maine"),
    MICHIGAN("MI", "Michigan"),
    MINNESOTA("MN", "Minnesota"),
    MISSOURI("MO", "Missouri"),
    MISSISSIPPI("MS", "Mississippi"),
    MONTANA("MT", "Montana"),
    NORTH_CAROLINA("NC", "North Carolina"),
    NORTH_DAKOTA("ND", "North Dakota"),
    NEBRASKA("NE", "Nebraska"),
    NEW_HAMPSHIRE("NH", "New Hampshire"),
    NEW_JERSEY("NJ", "New Jersey"),
    NEW_MEXICO("NM", "New Mexico"),
    NEVADA("NV", "Nevada"),
    NEW_YORK("NY", "New York"),
    OHIO("OH", "Ohio"),
    OKLAHOMA("OK", "Oklahoma"),
    OREGON("OR", "Oregon"),
    PENNSYLVANIA("PA", "Pennsylvania"),
    PUERTO_RICO("PR", "Puerto Rico"),
    RHODE_ISLAND("RI", "Rhode Island"),
    SOUTH_CAROLINA("SC", "South Carolina"),
    SOUTH_DAKOTA("SD", "South Dakota"),
    TENNESSEE("TN", "Tennessee"),
    TEXAS("TX", "Texas"),
    UTAH("UT", "Utah"),
    VIRGINIA("VA", "Virginia"),
    VERMONT("VT", "Vermont"),
    WASHINGTON("WA", "Washington"),
    WISCONSIN("WI", "Wisconsin"),
    WEST_VIRGINIA("WV", "West Virginia"),
    WYOMING("WY", "Wyoming");

    private static final Map<String, State> STATES_FROM_ABBREVIATIONS = new HashMap<>();
    static {
        for (State state : values()) {
            STATES_FROM_ABBREVIATIONS.put(state.abbreviation, state);
        }
    }

    private final String abbreviation;
    private final String name;

    private State(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public static State fromAbbreviation(String abbreviation) {
        return STATES_FROM_ABBREVIATIONS.get(abbreviation);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return abbreviation;
    }
}
