package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Random;

public class ProjectileHitHandler implements Listener {
    public EnhancedDifficulty plugin;

    public ProjectileHitHandler(EnhancedDifficulty plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        ManageSnowball(event);
        ManageSmallFireball(event);
    }

    private void ManageSnowball(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball
                && event.getEntity().getFireTicks() >= 0) {
            event.getHitEntity().setFireTicks(2 * 20);
        }
            ProjectileSource shooter = event.getEntity().getShooter();
        if (event.getHitEntity() instanceof Player
                && event.getEntity() instanceof Snowball
        && ((Snowman)shooter).isDerp()) {
            Player player = (Player) event.getHitEntity();
            int x = player.getLocation().getBlockX(), y = player.getLocation().getBlockY(), z = player.getLocation().getBlockZ();
            Location downLocation = new Location(player.getWorld(), x, y, z);
            downLocation.getBlock().setType(Material.COBWEB);
            Location upLocation = new Location(player.getWorld(), x, y + 1, z);
            upLocation.getBlock().setType(Material.SOUL_SAND);

            if (shooter instanceof Snowman) {
                Snowman snowman = (Snowman) shooter;
                snowman.damage(snowman.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2 + 1);
                snowman.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            }
        }
    }

    private void ManageSmallFireball(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof SmallFireball)) {
            return;
        }

        int snowGolemSpawnChance = 20;

        Random rnd = new Random();
        SmallFireball smallFireball = (SmallFireball) event.getEntity();

        int currentChance = rnd.nextInt(100);
        if (currentChance < snowGolemSpawnChance) {
            Snowman snowman = (Snowman) smallFireball.getWorld().spawnEntity(smallFireball.getLocation(), EntityType.SNOWMAN);

            Player result = null;
            double lastDistance = Double.MAX_VALUE;
            for (Player p : snowman.getWorld().getPlayers()) {
                double distance = snowman.getLocation().distance(p.getLocation());
                if (distance < lastDistance) {
                    lastDistance = distance;
                    result = p;
                }
            }

            if (result != null) {
                snowman.setTarget(result);
                snowman.setDerp(true);
            } else {
                snowman.remove();
            }
        }

        Player player = (Player) event.getHitEntity();
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.LEVITATION,
                1 * 20,
                2,
                true,
                true
        ));
    }
}
