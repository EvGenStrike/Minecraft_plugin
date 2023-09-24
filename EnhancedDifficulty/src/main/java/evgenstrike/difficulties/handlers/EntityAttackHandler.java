package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import evgenstrike.difficulties.Extensions.ArrayExtensions;
import evgenstrike.difficulties.lists.AllTools;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class EntityAttackHandler implements Listener {
    public int PhantomCount = 5;

    private final EnhancedDifficulty plugin;

    public EntityAttackHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onTNTExplode(EntityDamageByEntityEvent event){
        manageSkeleton(event);
        manageZombie(event);
        managePlayerAsDamager(event);
        managePlayerAsEntity(event);
        manageEnderman(event);
    }

    private void manageZombie(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (damager instanceof Zombie && entity instanceof Player){
            Player player = (Player) entity;
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.CONFUSION,
                    5*20,
                    0,
                    false,
                    false));
        }
    }

    private void manageSkeleton(EntityDamageByEntityEvent event){
        //Skeletons immune to explosions
        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
            if (event.getDamager() instanceof TNTPrimed && event.getEntity() instanceof Skeleton){
                event.setCancelled(true);
            }
        }
    }

    private void managePlayerAsDamager(EntityDamageByEntityEvent event){
        int witherWithSwordChance = 15;

        Entity entity = event.getEntity();
        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            Material itemMaterialInUse = player.getInventory().getItemInMainHand().getType();
            int swordPoisonMultiplier = ArrayExtensions.findIndex(AllTools.Swords, itemMaterialInUse);
            if (swordPoisonMultiplier == - 1){
                return;
            }
            swordPoisonMultiplier++;
            if (!(event.getEntity() instanceof LivingEntity)){
                return;
            }

            Random rnd = new Random();
            if (rnd.nextInt(100) < witherWithSwordChance){
                LivingEntity mob = (LivingEntity) entity;
                int durationInSeconds = swordPoisonMultiplier;
                int amplifier = swordPoisonMultiplier / 2;
                mob.addPotionEffect(new PotionEffect(
                        PotionEffectType.WITHER,
                        durationInSeconds * 20,
                        amplifier,
                        true,
                        true
                ));
                player.playSound(player, Sound.ENTITY_PARROT_IMITATE_WITHER, 1, 1);
            }
        }
    }

    private void managePlayerAsEntity(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof EnderDragon
        && event.getEntity() instanceof Player){
            Player player = (Player)event.getEntity();
            Location location = player.getLocation();
            int borderBound = 50;
            Object[] nearbyEntities = event.getEntity().getWorld().getNearbyEntities(
                    new BoundingBox(
                            location.getX() - borderBound,
                            location.getY() - borderBound,
                            location.getZ() - borderBound,
                            location.getX() + borderBound,
                            location.getY() + borderBound,
                            location.getZ() + borderBound
                    )
            ).toArray();
            for (int i = 0; i < nearbyEntities.length; i++){
                if (nearbyEntities[i] instanceof Enderman){
                    Enderman enderman = (Enderman) nearbyEntities[i];
                    enderman.setTarget(player);
                }
            }
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.HUNGER,
                    15*20,
                    20,
                    true,
                    true
            ));
        }

        if (event.getDamager() instanceof AreaEffectCloud
            && event.getEntity() instanceof Player
        && event.getEntity().getWorld().getEnvironment() == World.Environment.THE_END){
            Player player = (Player) event.getEntity();

            for (int i = 0; i < PhantomCount; i++){
                Phantom phantom = (Phantom) player.getWorld().spawnEntity(player.getLocation(), EntityType.PHANTOM);
                phantom.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(
                        phantom.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getValue() * 5);
            }
        }
    }

    private void manageEnderman(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Enderman
                || event.getEntity() instanceof Enderman
        ){
            int vexSpawnOnEndermanAttackChance = 20;

            Random rnd = new Random();
            int currentChance = rnd.nextInt(100);
            if (currentChance < vexSpawnOnEndermanAttackChance){
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.VEX);
            }
        }
    }
}
