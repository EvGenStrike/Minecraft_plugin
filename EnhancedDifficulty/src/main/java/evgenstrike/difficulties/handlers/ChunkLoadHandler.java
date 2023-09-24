package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadHandler implements Listener {
    private final EnhancedDifficulty plugin;

    public ChunkLoadHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if (!event.isNewChunk()){
            return;
        }

        int snowgolemsCount = 5;

        Entity[] allMobs = event.getChunk().getEntities();
        for (int i = 0; i < allMobs.length; i++){
            Entity mob = allMobs[i];
            if (mob.getType() == EntityType.IRON_GOLEM){
                for (int j = 0; j < snowgolemsCount; j++){
                    mob.getWorld().spawnEntity(mob.getLocation(), EntityType.SNOWMAN);
                }
            }
        }
    }
}
