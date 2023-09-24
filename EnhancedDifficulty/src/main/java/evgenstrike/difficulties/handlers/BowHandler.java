package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import evgenstrike.difficulties.specialFeatures.TNTBow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import evgenstrike.difficulties.specialFeatures.MultishotBow;
import evgenstrike.difficulties.specialFeatures.TNTBow;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BowHandler implements Listener{
    public EnhancedDifficulty plugin;
    public BowHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBowShot_Normal(EntityShootBowEvent event){
        if (event.getBow().getType() == Material.BOW ){
            MultishotBow.makeBowMultioshot(event, 5, 30);
            if (event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.HUNGER,
                        5 * 20,
                        15,
                        true,
                        true
                ));
            }
        }
    }

    @EventHandler
    public void onArrowLaunch(ProjectileLaunchEvent event){

    }
}
