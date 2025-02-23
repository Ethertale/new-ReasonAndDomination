package io.ethertale.reasonanddominationspringdefenseproject.guides.model;

public enum PostType {
    GEARGRIND_GROTTO("Geargrind Grotto"),
    IRONCLAD_DEPTHS("Ironclad Depths"),
    LEVIATHAN_CRUCIBLE("The Leviathan's Crucible"),
    EDISONSPIRE_FOUNDRY("Edisonspire Foundry"),
    ZEPHYR_NEXUS_CITADEL("Zephyr Nexus Citadel"),
    ARENA_1("Arena Season 1"),
    ARENA_2("Arena Season 2"),
    ARENA_3("Arena Season 3");

    private final String displayName;

    PostType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
