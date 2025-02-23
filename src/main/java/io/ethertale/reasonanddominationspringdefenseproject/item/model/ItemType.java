package io.ethertale.reasonanddominationspringdefenseproject.item.model;

public enum ItemType {
    BACK, CHEST, FEET, FINGER, HANDS, HEAD, LEGS, NECK, SHIELD, SHOULDER, TRINKET, WAIST, WRIST, ONE_HAND, TWO_HAND, OFF_HAND, RANGED;

    public String toDisplayName() {
        return this.name().replace("_", " ").toLowerCase().replaceFirst("[a-z]", String.valueOf(this.name().charAt(0))).replaceAll("_hand", " Hand");
    }
}
