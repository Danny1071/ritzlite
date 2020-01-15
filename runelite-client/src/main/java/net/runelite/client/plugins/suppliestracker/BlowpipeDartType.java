package net.runelite.client.plugins.suppliestracker;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static net.runelite.api.ItemID.*;

/**
 * Type of darts that can be put into the blowpipe
 */
@AllArgsConstructor
public enum BlowpipeDartType {
    BRONZE(BRONZE_DART), IRON(IRON_DART),
    STEEL(STEEL_DART), MITHRIL(MITHRIL_DART),
    ADAMANT(ADAMANT_DART), RUNE(RUNE_DART),
    DRAGON(DRAGON_DART);

    @Getter
    private int dartID;

}