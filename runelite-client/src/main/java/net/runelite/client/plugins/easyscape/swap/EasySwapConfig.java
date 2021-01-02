package net.runelite.client.plugins.easyscape.swap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.easyscape.util.DuelingRingMode;
import net.runelite.client.plugins.easyscape.util.EssenceMode;
import net.runelite.client.plugins.easyscape.util.GamesNecklaceMode;
import net.runelite.client.plugins.easyscape.util.GloryMode;

@ConfigGroup("easyswap")
public interface EasySwapConfig extends Config {
    @ConfigItem(
            keyName = "swapEasyDropBlisterwood",
            name = "Blisterwood logs easy drop",
            description = "Enables drop as the first option on the menu"
    )
    default boolean swapEasyDropBlisterwood() {
        return false;
    }

    @ConfigItem(
            keyName = "swapEasyDropRedwoods",
            name = "Redwoods easy drop",
            description = "Enables drop as the first option on the menu."
    )
    default boolean swapEasyDropRedwoods() {
        return false;
    }

    @ConfigItem(
            keyName = "swapEasyDropTeaks",
            name = "Teaks easy drop",
            description = "Enables drop as first option on the menu."
    )
    default boolean swapEasyDropTeaks() {
        return false;
    }

    @ConfigItem(
            keyName = "swapEasyDropFish",
            name = "Barb fishing easy drop",
            description = "Enables drop as the first option on the menu."
    )
    default boolean swapEasyDropFish() {
        return false;
    }

    @ConfigItem(
            keyName = "swapWildernessLever",
            name = "Wilderness Lever to Edgeville",
            description = "Swap Edgeville Lever as default for wilderness lever"
    )
    default boolean swapWildernessLever() {
        return true;
    }

    @ConfigItem(
            keyName = "swapDream",
            name = "Swap Dominic Onion",
            description = "Enables swapping from talk-to to Dream.",
            position = 1
    )

    default boolean getSwapDream() {
        return true;
    }


    @ConfigItem(
            keyName = "swapSmithing",
            name = "Swap Smithing",
            description = "Enables swapping of smith-1 and smith-all options.",
            position = 2
    )

    default boolean getSwapSmithing() {
        return true;
    }

    @ConfigItem(
            keyName = "swapTanning",
            name = "Swap Tanning",
            description = "Enables swapping of tan-1 and tan-all options.",
            position = 3
    )

    default boolean getSwapTanning() {
        return true;
    }

    @ConfigItem(
            keyName = "swapCrafting",
            name = "Swap Crafting",
            description = "Enables swapping of Make-1 and Make-all options.",
            position = 4
    )

    default boolean getSwapCrafting() {
        return true;
    }

    @ConfigItem(
            keyName = "swapArdougneCape",
            name = "Swap Ardougne Cape",
            description = "Enables swapping of teleport and wear.",
            position = 5
    )

    default boolean getSwapArdougneCape() {
        return true;
    }

    @ConfigItem(
            keyName = "swapSawmill",
            name = "Swap Sawmill Operator",
            description = "Makes Buy-plank the default option on the sawmill operator.",
            position = 6
    )

    default boolean getSwapSawmill() {
        return true;
    }

    @ConfigItem(
            keyName = "swapSawmillPlanks",
            name = "Swap Buy Planks",
            description = "Makes Buy All the default option in buy planks.",
            position = 7
    )

    default boolean getSwapSawmillPlanks() {
        return true;
    }

    @ConfigItem(
            keyName = "swapPuroPuro",
            name = "Swap Puro Puro Wheat",
            description = "",
            position = 8
    )

    default boolean getSwapPuro() {
        return true;
    }

    @ConfigItem(
            keyName = "swapEssencePounch",
            name = "Swap Essence Pouch",
            description = "",
            position = 9
    )

    default boolean getSwapEssencePouch() {
        return true;
    }

    @ConfigItem(
            keyName = "essenceMode",
            name = "Mode",
            description = "",
            position = 10
    )

    default EssenceMode getEssenceMode() {
        return EssenceMode.RUNECRAFTING;
    }
/*
    @ConfigItem(
            keyName = "swapGamesNecklace",
            name = "Swap Games Necklace",
            description = "",
            position = 11
    )
    default boolean getGamesNecklace() {
        return true;
    }

    @ConfigItem(
            keyName = "gamesNecklaceMode",
            name = "Mode",
            description = "",
            position = 12
    )

    default GamesNecklaceMode getGamesNecklaceMode() {
        return GamesNecklaceMode.BURTHORPE;
    }

    @ConfigItem(
            keyName = "sgamesNecklaceMode",
            name = "Shift Mode",
            description = "",
            position = 13
    )

    default GamesNecklaceMode getSGamesNecklaceMode() {
        return GamesNecklaceMode.BARBARIAN_OUTPOST;
    }

    @ConfigItem(
            keyName = "swapDuelingRing",
            name = "Swap Dueling Ring",
            description = "",
            position = 14
    )

    default boolean getDuelingRing() {
        return true;
    }

    @ConfigItem(
            keyName = "duelingRingMode",
            name = "Mode",
            description = "",
            position = 15
    )

    default DuelingRingMode getDuelingRingMode() {
        return DuelingRingMode.DUEL_ARENA;
    }

    @ConfigItem(
            keyName = "shiftduelingRingMode",
            name = "Shift Mode",
            description = "",
            position = 16
    )

    default DuelingRingMode getSDuelingRingMode() {
        return DuelingRingMode.CASTLE_WARS;
    }

    @ConfigItem(
            keyName = "swapGlory",
            name = "Swap Glory",
            description = "",
            position = 17
    )

    default boolean getGlory() {
        return true;
    }

    @ConfigItem(
            keyName = "gloryMode",
            name = "Mode",
            description = "",
            position = 18
    )

    default GloryMode getGloryMode() {
        return GloryMode.EDGEVILLE;
    }

    @ConfigItem(
            keyName = "sgloryMode",
            name = "Shift Mode",
            description = "",
            position = 19
    )

    default GloryMode getSGloryMode() {
        return GloryMode.KARAMJA;
    }*/
}
