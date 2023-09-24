package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HungerLevelHandler implements Listener {
    private final EnhancedDifficulty plugin;

    public HungerLevelHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onHungerLevelChange(FoodLevelChangeEvent event){
        if (event.getFoodLevel() <= 6){
            event.getEntity().addPotionEffect(new PotionEffect(
                    PotionEffectType.WEAKNESS,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false
            ));
        }
        else{
            event.getEntity().removePotionEffect(PotionEffectType.WEAKNESS);
        }
    }
}
