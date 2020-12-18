package net.runelite.client.plugins.npcspawns;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.swing.JButton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.devtools.DevToolsPlugin;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import static net.runelite.client.RuneLite.RUNELITE_DIR;

@PluginDescriptor(
    name = "Npc Spawns"
)
@Slf4j
public class NpcSpawnsPlugin extends Plugin
{

    public static final File SPAWNS_DIR = new File(RUNELITE_DIR, "spawns");

    @Inject
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private NpcSpawnsOverlay NPCSpawnsOverlay;

    @Getter
    private NpcSpawnsButton enabled;
    @Getter
    private JButton saveSpawns;
    @Getter
    private JButton loadSpawns;
    @Getter
    private JButton clearSpawns;
    private NavigationButton navButton;

    /**
     * A map of {@link NPC#getIndex()} to a {@link NpcSpawn}.
     */
    @Getter(AccessLevel.PACKAGE)
    private Map<Integer, NpcSpawn> spawns = new HashMap<>();

    /**
     * Resets every tick, used to show the amount of updates in a single tick.
     */
    @Getter(AccessLevel.PACKAGE)
    private int updatedThisTick = 0;

    @Override
    protected void startUp() throws Exception
    {
        if(!SPAWNS_DIR.exists())
            SPAWNS_DIR.mkdirs();
        enabled = new NpcSpawnsButton("Enabled");

        saveSpawns = new JButton("Save Spawns");
        saveSpawns.addActionListener(e ->
        {
            try
            {
//                Path spawnsPath = Paths.get(SPAWNS_DIR.getPath() + "/npc_spawns.json");
//                Files.write(spawnsPath, new Gson().toJson(spawns.values()).getBytes());
                BufferedWriter writer = new BufferedWriter(new FileWriter(SPAWNS_DIR.getPath() + "/npc_spawns.json"));
                spawns.forEach((key, value) -> {
                    try {
                        WorldPoint point = value.getPoints().stream().findFirst().orElse(null);
                        if (point == null) {
                            return;
                        }
                        writer.write(value.getNpc() + " " + point.getX() + " " + point.getY() + " " + point.getPlane() + " WEST " + (value.getPoints().size() > 1 ? "true" : "false") + " // " + client.getNpcDefinition(value.getNpc()).getName());
                        writer.newLine();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                });
                writer.flush();
                writer.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });

        loadSpawns = new JButton("Load Spawns");
        loadSpawns.addActionListener(e ->
        {
            try
            {
                List<NpcSpawn> tempSpawns = new Gson().fromJson(new InputStreamReader(new FileInputStream(SPAWNS_DIR.getPath() + "/npc_spawns.json")), new TypeToken<List<NpcSpawn>>() {}.getType());

                int points = 0;
                spawns.clear();
                for (NpcSpawn spawn : tempSpawns)
                {
                    points += spawn.getPoints().size();
                    spawns.put(spawn.getIndex(), spawn);
                }
                System.out.println("Loaded " + spawns.size() + " spawns with " + points + " points.");
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
        });

        clearSpawns = new JButton("Clear Spawns");
        clearSpawns.addActionListener(e -> spawns.clear());

        final NpcSpawnsPanel panel = injector.getInstance(NpcSpawnsPanel.class);

        final BufferedImage icon = ImageUtil.getResourceStreamFromClass(DevToolsPlugin.class, "devtools_icon.png");

        overlayManager.add(NPCSpawnsOverlay);

        navButton = NavigationButton.builder()
            .tooltip("Npc Spawns")
            .icon(icon)
            .priority(1)
            .panel(panel)
            .build();

        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(NPCSpawnsOverlay);
        clientToolbar.removeNavigation(navButton);
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        // check if the dumper is active
        if (!enabled.isActive())
        {
            return;
        }

        NPC npc = event.getNpc();
        NPCComposition def = client.getNpcDefinition(event.getNpc().getId());
        NpcSpawn existSpawn = spawns.get(npc.getIndex());

        if (existSpawn != null && existSpawn.getNpc() == npc.getId())
        {
            // spawn exists and npc ids are the same
            return;
        }

        NpcSpawn spawn = new NpcSpawn(npc.getId(), npc.getIndex());
        spawn.setOrientation(npc.getOrientation());
        spawn.getPoints().add(npc.getWorldLocation());

        spawns.put(npc.getIndex(), spawn);

        if (existSpawn != null)
        {
            log.debug("Replaced " + existSpawn.getNpc() + " with " + spawn.getNpc() + " due to same index but different ids.");
        }
        else
        {
            log.debug("Added new NPC to spawns: index=" + npc.getIndex() + ", id=" + npc.getId() + ", name=" + def.getName() + "");
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        updatedThisTick = 0;

        // check if the dumper is active
        if (!enabled.isActive())
        {
            return;
        }

        for (NPC npc : client.getNpcs())
        {
            NpcSpawn spawn = spawns.get(npc.getIndex());

            if (spawn == null)
            {
                continue;
            }

            if (spawn.getOrientation() != -1 && npc.getOrientation() != spawn.getOrientation())
            {
                spawn.setOrientation(-1);
            }

            if (spawn.getPoints().add(npc.getWorldLocation()))
            {
                updatedThisTick++;
            }
        }
    }
}