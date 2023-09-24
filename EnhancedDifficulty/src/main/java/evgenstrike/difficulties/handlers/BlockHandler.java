package evgenstrike.difficulties.handlers;

import evgenstrike.difficulties.EnhancedDifficulty;
import evgenstrike.difficulties.specialFeatures.RandomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockHandler implements Listener {

    public static float RareItemDropChance = 0.1f;
    public static float SilverfishFromStoneChance = 1f;

    private final EnhancedDifficulty plugin;

    public BlockHandler(EnhancedDifficulty plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ManageWood(event);
        ManageStone(event);
        ManageIron(event);
    }

    private void ManageWood(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material mainHandItemType = player.getInventory().getItemInMainHand().getType();
        Material brokenBlockType = event.getBlock().getType();
        if (brokenBlockType.toString().endsWith("_LOG")) {
            if (mainHandItemType.toString().endsWith("_AXE")) {
                return;
            } else {
                player.damage(1);
            }
        }
    }

    private void ManageStone(BlockBreakEvent event) {
        Material brokenBlockType = event.getBlock().getType();
        if (brokenBlockType == Material.STONE || brokenBlockType == Material.SAND || brokenBlockType == Material.DIRT) {
            Random rnd = new Random();
            double currentChance = rnd.nextDouble() * 100;

            if (currentChance <= RareItemDropChance) {
                ItemStack randomItem = rnd.nextInt(2) == 0 ? RandomItems.GetRandomTool() : RandomItems.GetRandomArmor();
                randomItem = RandomItems.SetRandomDurability(randomItem);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().dropItemNaturally(
                        event.getBlock().getLocation(),
                        randomItem
                );
                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 0.5f);
                Bukkit.getLogger().info(
                        "Rare item from stone: "
                                + currentChance
                                + " "
                                + RareItemDropChance
                                + " "
                                + event.getPlayer().getName()
                                + " "
                                + randomItem.toString());
            }

            if (currentChance <= SilverfishFromStoneChance){
                event.getBlock().getWorld().spawnEntity(event.getPlayer().getLocation().add(0, 1, 0), EntityType.SILVERFISH);
                event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.5f);
            }
        }
    }

    private void ManageIron(BlockBreakEvent event) {
        Material brokenBlockType = event.getBlock().getType();
        if (brokenBlockType == Material.IRON_ORE
        || brokenBlockType == Material.DEEPSLATE_IRON_ORE) {
            int doubleIronChance = 10;

            Random rnd = new Random();
            ItemStack iron = new ItemStack(Material.RAW_IRON);
            iron.setAmount(rnd.nextInt(100) <= doubleIronChance ? 2 : 1);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(
                    event.getBlock().getLocation(),
                    iron
            );
        }
    }


}