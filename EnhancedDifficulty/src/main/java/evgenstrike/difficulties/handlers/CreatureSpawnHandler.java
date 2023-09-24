package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class CreatureSpawnHandler implements Listener
{
    public static int SkeletonSpawnInEndChance = 5;
    public static int RavagerSpawnChance = 5;

    private final EnhancedDifficulty plugin;

    public CreatureSpawnHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        ManageSpider(event);
        ManageSkeleton(event);
        ManageZombie(event);
        ManageVex(event);
        ManageCreeper(event);
        ManageIronGolem(event);
        ManageSnowGolem(event);
        ManagePiglin(event);
        ManageGhast(event);
        ManageWitherSkeleton(event);
        ManageEnderman(event);
    }

    private void ManageIronGolem(CreatureSpawnEvent event) {
        if (event.getEntity().getType() != EntityType.IRON_GOLEM){
            return;
        }
        event.getEntity().addPotionEffect(new PotionEffect(
                PotionEffectType.REGENERATION,
                Integer.MAX_VALUE,
                0,
                false,
                false
        ));

        int snowgolemsCount = 5;

        for (int j = 0; j < snowgolemsCount; j++){
            event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SNOWMAN);
        }
    }

    private void ManageSpider(CreatureSpawnEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Spider && !(entity instanceof CaveSpider)){
            Spider spider = (Spider)entity;
            spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.45);
            entity.getWorld().spawnEntity(
                    spider.getLocation().add(new Vector(1, 1, 1)),
                    EntityType.CAVE_SPIDER);
        }
    }

    private void ManageSkeleton(CreatureSpawnEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Skeleton){
            ((Skeleton)entity).setHealth(2);
        }
    }

    private void ManageZombie(CreatureSpawnEvent event){
        LivingEntity entity = event.getEntity();
        if (entity instanceof Zombie){
            Random rnd = new Random();
            if (rnd.nextInt(100) < RavagerSpawnChance){
                event.getEntity().getWorld().spawnEntity(entity.getLocation(), EntityType.RAVAGER);

                event.setCancelled(true);
                return;
            }
            ItemStack frostBoots = new ItemStack(Material.IRON_BOOTS);
            frostBoots.addEnchantment(Enchantment.FROST_WALKER, 2);
            frostBoots.addEnchantment(Enchantment.DURABILITY, 2);

            ItemStack fireSword = new ItemStack(Material.IRON_SWORD);
            fireSword.addEnchantment(Enchantment.FIRE_ASPECT, 2);

            entity.getEquipment().setBoots(frostBoots);
            entity.getEquipment().setBootsDropChance(0);
            entity.getEquipment().setItemInMainHand(fireSword);
            entity.getEquipment().setItemInOffHand(fireSword);
            entity.getEquipment().setItemInMainHandDropChance(0);
            entity.getEquipment().setItemInOffHandDropChance(0);
        }
    }

    private void ManageVex(CreatureSpawnEvent event){
        LivingEntity entity = event.getEntity();
        if (entity instanceof Vex){
            entity.setHealth(1);
        }
    }

    //Default speed = 0.25
    private void ManageCreeper(CreatureSpawnEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Creeper){
            Creeper creeper = (Creeper)entity;
            creeper.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.38);

        }
    }

    private void ManageSnowGolem(CreatureSpawnEvent event){
        if (event.getEntity().getType() == EntityType.SNOWMAN){
            Snowman snowman = (Snowman) event.getEntity();
            snowman.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        }
    }

    private void ManagePiglin(CreatureSpawnEvent event){
        if (event.getEntity().getType() != EntityType.PIGLIN){
            return;
        }


        Random rnd = new Random();
        if (rnd.nextInt(2) == 0){
            ItemStack goldenSword = new ItemStack(Material.GOLDEN_SWORD);
            goldenSword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
            event.getEntity().getEquipment().setItemInMainHand(goldenSword);
            event.getEntity().getEquipment().setItemInMainHandDropChance(0);
        }
        else{
            ItemStack crossbow = new ItemStack(Material.CROSSBOW);
            crossbow.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 4);
            crossbow.addUnsafeEnchantment(Enchantment.MULTISHOT, 1);
            crossbow.addUnsafeEnchantment(Enchantment.PIERCING, 4);
            event.getEntity().getEquipment().setItemInMainHand(crossbow);
            event.getEntity().getEquipment().setItemInMainHandDropChance(0);
        }
    }

    private void ManageGhast(CreatureSpawnEvent event){
        if (event.getEntity().getType() != EntityType.GHAST){
            return;
        }
        Ghast ghast = (Ghast)event.getEntity();
    }

    private void ManageWitherSkeleton(CreatureSpawnEvent event){
        if (!(event.getEntity() instanceof WitherSkeleton)){
            return;
        }
        WitherSkeleton witherSkeleton = (WitherSkeleton) event.getEntity();

        ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD);
        netheriteSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        netheriteSword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);

        ItemStack netheriteChestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        netheriteChestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        netheriteChestplate.addUnsafeEnchantment(Enchantment.THORNS, 3);

        witherSkeleton.getEquipment().setItemInMainHand(netheriteSword);
        witherSkeleton.getEquipment().setItemInOffHand(netheriteSword);
        witherSkeleton.getEquipment().setItemInMainHandDropChance(0);
        witherSkeleton.getEquipment().setItemInOffHandDropChance(0);
        witherSkeleton.getEquipment().setChestplate(netheriteChestplate);
        witherSkeleton.getEquipment().setChestplateDropChance(0);
    }

    private void ManageEnderman(CreatureSpawnEvent event){
        if (event.getEntity() instanceof Enderman
        && event.getEntity().getWorld().getEnvironment() == World.Environment.THE_END){
            Random rnd = new Random();
            if (rnd.nextInt(100) < SkeletonSpawnInEndChance){
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SKELETON);
            }
        }
    }
}
