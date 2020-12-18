package net.runelite.client.plugins.npcspawns;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import net.runelite.api.coords.WorldPoint;

@Data
public class NpcSpawn {

    private final int npc;
    private final int index;
    private final Set<WorldPoint> points = new HashSet<>();
    private int orientation;

}