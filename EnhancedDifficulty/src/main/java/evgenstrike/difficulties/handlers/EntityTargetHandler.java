package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

public class EntityTargetHandler implements Listener {
    private final EnhancedDifficulty plugin;

    public EntityTargetHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        ManageSnowmanAndIronGolem(event);
        ManageSnowmanAndBlaze(event);
        ManageSnowmanAndWitherSkeleton(event);
        ManageSnowmanAndMagmaCube(event);
    }

    private void ManageSnowmanAndIronGolem(EntityTargetEvent event){
        if (event.getEntity().getType() == EntityType.SNOWMAN
                && event.getTarget().getType() == EntityType.IRON_GOLEM){
            event.setCancelled(true);
        }

        if (event.getEntity().getType() == EntityType.IRON_GOLEM
                && event.getTarget().getType() == EntityType.SNOWMAN){
            event.setCancelled(true);
        }
    }

    private void ManageSnowmanAndBlaze(EntityTargetEvent event){
        if (event.getEntity().getType() == EntityType.SNOWMAN
                && event.getTarget().getType() == EntityType.BLAZE){
            event.setCancelled(true);
        }

        if (event.getEntity().getType() == EntityType.BLAZE
                && event.getTarget().getType() == EntityType.SNOWMAN){
            event.setCancelled(true);
        }
    }

    private void ManageSnowmanAndWitherSkeleton(EntityTargetEvent event){
        if (event.getEntity().getType() == EntityType.SNOWMAN
                && event.getTarget().getType() == EntityType.WITHER_SKELETON){
            event.setCancelled(true);
        }

        if (event.getEntity().getType() == EntityType.WITHER_SKELETON
                && event.getTarget().getType() == EntityType.SNOWMAN){
            event.setCancelled(true);
        }
    }

    private void ManageSnowmanAndMagmaCube(EntityTargetEvent event){
        if (event.getEntity().getType() == EntityType.SNOWMAN
                && event.getTarget().getType() == EntityType.MAGMA_CUBE){
            event.setCancelled(true);
        }

        if (event.getEntity().getType() == EntityType.MAGMA_CUBE
                && event.getTarget().getType() == EntityType.SNOWMAN){
            event.setCancelled(true);
        }
    }
}
