package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageHandler implements Listener {
    public static int PhantomCount = 5;

    private final EnhancedDifficulty plugin;

    public EntityDamageHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
//        if (event.getEntity() instanceof Player
//        && event.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH){
//            Player player = (Player)event.getEntity();
//            player.addPotionEffect(new PotionEffect(
//                    PotionEffectType.POISON,
//                    60*20,
//                    1,
//                    true,
//                    true
//            ));
//
//
//        }
    }
}
