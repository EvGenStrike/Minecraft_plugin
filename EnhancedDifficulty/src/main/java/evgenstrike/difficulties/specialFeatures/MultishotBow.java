package evgenstrike.difficulties.specialFeatures;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MultishotBow {
    public static void makeBowMultioshot(EntityShootBowEvent event, int arrowCount, double maxArrowRotation){
        LivingEntity entity = event.getEntity();
        Entity projectile = event.getProjectile();
        AbstractArrow arrow = (AbstractArrow)projectile;

        List<Arrow> arrows = new ArrayList<Arrow>();

        double currentAngle = maxArrowRotation;
        double rotateBy = (maxArrowRotation * 2) / (arrowCount - 1);
        for (int i = 0; i < arrowCount; i++){
                Arrow newArrow = spawnArrowForMultishotBow(entity, arrow, currentAngle);
                newArrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                arrows.add(newArrow);
                newArrow.setPierceLevel(127);
            currentAngle -= rotateBy;
        }

        TNTBow TNTBowObj = new TNTBow(20);
        if (!(event.getEntity() instanceof Player)){
            TNTBowObj.StartLoop(arrows.get(arrows.size() / 2 + 1));
        }
        for (int i = 0; i < arrows.size(); i++){
            addPotionsToArrow(arrows.get(i));
            if (event.getEntity() instanceof Player){
                continue;
            }

        }
    }

    public static Arrow spawnArrowForMultishotBow(
            LivingEntity entity,
            AbstractArrow originalArrow,
            double rotationDegrees){
        Arrow newArrow = entity.getWorld().spawn(entity.getEyeLocation(), Arrow.class);
        newArrow.setDamage(originalArrow.getDamage());
        newArrow.setShooter(entity);
        newArrow.setVelocity(originalArrow.getVelocity().rotateAroundY(Math.toRadians(rotationDegrees)));
        return newArrow;
    }

    public static void addPotionsToArrow(Arrow arrow){
        arrow.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 1*20, 15), true);
        arrow.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 2*20, 5), true);
    }
}

// DEFAULTS:
// Knockback: 2.0
// Damage: 0