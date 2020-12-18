package net.runelite.client.plugins.npcspawns;

import java.awt.GridLayout;
import javax.inject.Inject;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class NpcSpawnsPanel extends PluginPanel
{

    private final NpcSpawnsPlugin plugin;

    @Inject
    private NpcSpawnsPanel(NpcSpawnsPlugin plugin)
    {
        super();
        this.plugin = plugin;

        setBackground(ColorScheme.DARK_GRAY_COLOR);

        add(createOptionsPanel());
    }

    private JPanel createOptionsPanel()
    {
        final JPanel container = new JPanel();
        container.setBackground(ColorScheme.DARK_GRAY_COLOR);
        container.setLayout(new GridLayout(0, 2, 3, 3));

        container.add(plugin.getEnabled());

        container.add(plugin.getSaveSpawns());
        container.add(plugin.getLoadSpawns());
        container.add(plugin.getClearSpawns());

        return container;
    }

}