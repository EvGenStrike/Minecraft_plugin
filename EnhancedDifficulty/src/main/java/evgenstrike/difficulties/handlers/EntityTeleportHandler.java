package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

import java.util.Random;

public class EntityTeleportHandler implements Listener {
    public int VexSpawnChance = 60;

    private final EnhancedDifficulty plugin;

    public EntityTeleportHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event){
        if (event.getEntity() instanceof Enderman){
            Random rand = new Random();
            if (rand.nextInt(100) < VexSpawnChance){
                event.getEntity().getWorld().spawnEntity(
                        event.getEntity().getLocation(),
                        EntityType.VEX
                );
            }
        }
    }
}
