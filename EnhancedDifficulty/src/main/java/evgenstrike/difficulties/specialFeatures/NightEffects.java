package evgenstrike.difficulties.specialFeatures;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class NightEffects {
    public static boolean isDay() {
        Server server = getServer();
        long time = server.getWorld("world").getTime();

        if(time >= 0 && time < 12300) {
            return true;
        } else {
            return false;
        }
    }

    public static void onNightArrival(){
        List<Player> players = Bukkit.getServer().getWorld("world").getPlayers();
        if (!NightEffects.isDay()){
            for (int i = 0; i < players.size(); i++){
                Player player = players.get(i);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 10*20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10*20, 0));
            }
        }
    }
}

