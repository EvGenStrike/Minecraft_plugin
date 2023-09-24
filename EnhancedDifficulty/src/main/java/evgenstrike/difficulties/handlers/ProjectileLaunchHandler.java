package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class ProjectileLaunchHandler implements Listener {
    public EnhancedDifficulty plugin;

    public ProjectileLaunchHandler(EnhancedDifficulty plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        ManageSnowball(event);
        ManageGhastFire(event);
    }

    private void ManageSnowball(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Snowball)
                || event.getEntity().getShooter() instanceof Player) {
            return;
        }
        event.getEntity().setFireTicks(10 * 60);
    }

    private void ManageGhastFire(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() != EntityType.FIREBALL
                || Objects.equals(event.getEntity().getCustomName(), "not valid")) {
            return;
        }
        int spawnCount = 5;
        int maxSpawnRotation = 300;

        double currentAngle = maxSpawnRotation;
        double rotateBy = (maxSpawnRotation * 2) / (spawnCount - 1);
        Fireball fireball = (Fireball) event.getEntity();
        for (int i = 0; i < spawnCount; i++) {
            spawnTnt(fireball, currentAngle);
            spawnCreepers(fireball, currentAngle);
            spawnArrowForMultishotBow(fireball, currentAngle);
            //spawnWitherSkull(fireball, currentAngle);

            currentAngle -= rotateBy;
        }
    }

    public void spawnTnt(
            Fireball originalFireball,
            double rotationDegrees) {
        World world = originalFireball.getWorld();
        Location location = originalFireball.getLocation();
        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT);
        tnt.setVelocity(originalFireball.getLocation().getDirection().rotateAroundY(Math.toRadians(rotationDegrees)));
        tnt.setCustomName("noExplosiveTnt");
    }

    public void spawnCreepers(Fireball originalFireball,
                              double rotationDegrees){
        World world = originalFireball.getWorld();
        Location location = originalFireball.getLocation();
        Creeper creeper = (Creeper) world.spawnEntity(location, EntityType.CREEPER);
        creeper.setVelocity(originalFireball.getLocation().getDirection().rotateAroundY(Math.toRadians(rotationDegrees)));
        creeper.setCustomName("noExplosiveTnt");
        creeper.setCustomNameVisible(false);
        creeper.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW_FALLING,
                10*20,
                0,
                false,
                false
        ));
    }

    public void spawnWitherSkull(Fireball originalFireball,
                                 double rotationDegrees){
        World world = originalFireball.getWorld();
        Location location = originalFireball.getLocation();
        WitherSkull witherSkull = (WitherSkull) world.spawnEntity(location, EntityType.WITHER_SKULL);
        witherSkull.setVelocity(originalFireball.getLocation().getDirection().rotateAroundY(Math.toRadians(rotationDegrees)));
        witherSkull.setCustomName("noExplosiveTnt");
        witherSkull.setCustomNameVisible(false);
    }

    public static Arrow spawnArrowForMultishotBow(
            Fireball originalFireball,
            double rotationDegrees){
        World world = originalFireball.getWorld();
        Location location = originalFireball.getLocation();
        Arrow newArrow = (Arrow) world.spawnEntity(location, EntityType.ARROW);
        newArrow.setDamage(2);
        newArrow.setVelocity(originalFireball.getVelocity().rotateAroundY(Math.toRadians(rotationDegrees)));
        newArrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
        return newArrow;
    }

}
