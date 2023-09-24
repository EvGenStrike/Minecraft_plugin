package evgenstrike.difficulties.specialFeatures;

import evgenstrike.difficulties.lists.AllArmor;
import evgenstrike.difficulties.lists.AllEnchantments;
import evgenstrike.difficulties.lists.AllTools;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;

public class RandomItems {
    public static ItemStack GetRandomTool(){
        Random rnd = new Random();
        Material[][] allTools = AllTools.Everything;
        Material[] randomType = allTools[rnd.nextInt(allTools.length)];
        Material randomMaterial = randomType[rnd.nextInt(randomType.length)];
        ItemStack randomlyEnchantedTool = GetRandomlyEnchantedItem(new ItemStack(randomMaterial));
        return randomlyEnchantedTool;
    }

    public static ItemStack GetRandomArmor(){
        Random rnd = new Random();
        Material[][] allArmor = AllArmor.Everything;
        Material[] randomType = allArmor[rnd.nextInt(allArmor.length)];
        Material randomMaterial = randomType[rnd.nextInt(randomType.length)];
        ItemStack randomlyEnchantedArmor = GetRandomlyEnchantedItem(new ItemStack(randomMaterial));
        return randomlyEnchantedArmor;
    }

    public static ItemStack GetRandomlyEnchantedItem(ItemStack tool){
        int maxEnchantmentsCount = 3;

        Random rnd = new Random();
        int enchantmentCount = rnd.nextInt(maxEnchantmentsCount);
        for (int i = 0; i < enchantmentCount * 5; i++){
            Enchantment randomEnchantment = AllEnchantments.Enchantments[
                    rnd.nextInt(AllEnchantments.Enchantments.length)
                    ];
            if (!randomEnchantment.canEnchantItem(tool)
                    || tool.getEnchantments().size() >= 3){
                continue;
            }
            int enchantmentLevel = rnd.nextInt(randomEnchantment.getMaxLevel()) + 1;
            tool.addEnchantment(randomEnchantment, enchantmentLevel);
        }

        return tool;
    }

    public static ItemStack SetRandomDurability(ItemStack item){
        Random rnd = new Random();
        short randomDamage = (short)rnd.nextInt(item.getType().getMaxDurability());
        if (item.getItemMeta() instanceof Damageable){
            Damageable meta = (Damageable)(item.getItemMeta());
            meta.setDamage(randomDamage);
            item.setItemMeta(meta);
        }
        return item;
    }
}
