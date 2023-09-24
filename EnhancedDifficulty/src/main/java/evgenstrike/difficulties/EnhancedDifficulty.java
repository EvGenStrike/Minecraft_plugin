package evgenstrike.difficulties;

import evgenstrike.difficulties.handlers.*;
import evgenstrike.difficulties.specialFeatures.NightEffects;
import evgenstrike.difficulties.specialFeatures.TNTBow;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnhancedDifficulty extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Hello, world!");

        new BowHandler(this);
        new TNTBow(this);
        new BedHandler(this);
        new BlockHandler(this);
        new EntityAttackHandler(this);
        new CreeperExplosionHandler(this);
        new CreatureSpawnHandler(this);
        new EntityTeleportHandler(this);
        new ItemConsumeHandler(this);
        new HungerLevelHandler(this);
        new EnchantItemHandler(this);
        new ChunkLoadHandler(this);
        new EntityTargetHandler(this);
        new ProjectileLaunchHandler(this);
        new ProjectileHitHandler(this);
        new EntityDamageHandler(this);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                NightEffects.onNightArrival();
            }

        }, 100L, 100L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("shutting down");
    }
}
