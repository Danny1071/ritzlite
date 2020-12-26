package net.runelite.api.events;

import lombok.Data;
import net.runelite.api.widgets.Widget;

/**
 * @author Danny
 */
@Data
public class WidgetHiddenChanged {

    /**
     * The affect widget
     */
    private Widget widget;

    /**
     * The new hidden state of the widget
     */
    private boolean hidden;
}
