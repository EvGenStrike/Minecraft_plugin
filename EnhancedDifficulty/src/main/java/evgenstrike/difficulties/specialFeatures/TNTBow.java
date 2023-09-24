package evgenstrike.difficulties.specialFeatures;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class TNTBow implements Listener {

    public TNTBow(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    public TNTBow(float tntSpawnDelay){
        this.plugin = (EnhancedDifficulty)Bukkit.getServer().getPluginManager().getPlugin("enhancedDifficulty");
        this.tntSpawnDelay = tntSpawnDelay;
    }

    public EnhancedDifficulty plugin;
    public float tntSpawnDelay;

    public boolean isLoopGoing = false;

    public void StartLoop(Arrow arrow){
        isLoopGoing = true;
        ArrowLoop(arrow);
    }

    private void StopLoop(){
        isLoopGoing = false;
    }

    public void ArrowLoop(final Arrow arrow){
        new BukkitRunnable(){
            private int counter = 1000;
            private int blowupTime = 80;

            @Override
            public void run(){
                if (!isLoopGoing){
                    cancel();
                    return;
                }

                if (counter < 3 || arrow.isOnGround()){
                    arrow.remove();
                    StopLoop();
                    return;
                }

                if (counter % tntSpawnDelay == 0){
                    if (blowupTime < 3){
                        return;
                    }
                    TNTPrimed tnt = (TNTPrimed)arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.PRIMED_TNT);
                    tnt.setFuseTicks(blowupTime);
                    tnt.setCustomName("noExplosiveTnt");
                }
                counter--;
                blowupTime--;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event) {
        if (Objects.equals(event.getEntity().getCustomName(), "noExplosiveTnt")){
            event.blockList().clear();

        }
        //if (!(event.getEntity() instanceof TNTPrimed)) return;
    }


}
