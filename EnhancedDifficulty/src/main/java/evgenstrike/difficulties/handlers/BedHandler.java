package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import evgenstrike.difficulties.specialFeatures.NightEffects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BedHandler implements Listener {

    private final EnhancedDifficulty plugin;

    public BedHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBedExit(PlayerBedLeaveEvent event){
        if (NightEffects.isDay()){
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60*20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60*20, 0));
        }

    }
}
