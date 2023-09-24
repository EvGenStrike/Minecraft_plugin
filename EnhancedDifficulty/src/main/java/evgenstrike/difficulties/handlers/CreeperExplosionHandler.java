package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class CreeperExplosionHandler implements Listener {
    private final EnhancedDifficulty plugin;

    public int ChargedCreepersCount = 2;

    public CreeperExplosionHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreeperExplosion(EntityExplodeEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Creeper && !((Creeper)entity).isPowered()){
            for (int i = 0; i < ChargedCreepersCount; i++){
                SpawnChargedCreeper(event);
            }
        }
    }

    private void SpawnChargedCreeper(EntityExplodeEvent event){
        Entity entity = event.getEntity();
        Creeper creeper = (Creeper)entity.getWorld().spawnEntity(
                event.getLocation().add(new Vector(1, 1, 1)),
                EntityType.CREEPER);
        creeper.setPowered(true);
    }
}
