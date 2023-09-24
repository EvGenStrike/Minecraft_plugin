package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnchantItemHandler implements Listener {
    private final EnhancedDifficulty plugin;

    public EnchantItemHandler(EnhancedDifficulty plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event){
        Map<Enchantment, Integer> enchantments = event.getEnchantsToAdd();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + enchantments.size());
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()){
            entry.setValue(entry.getValue() + 1);
        }
    }

    @EventHandler
    public void onItemEnchantOFfer(PrepareItemEnchantEvent event){
        EnchantmentOffer[] offers = event.getOffers();
        for (int i = 0; i < offers.length; i++){
            offers[i].setEnchantmentLevel(offers[i].getEnchantmentLevel() + 1);
        }
    }
}
