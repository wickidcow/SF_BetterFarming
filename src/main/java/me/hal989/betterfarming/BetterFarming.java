package me.hal989.betterfarming;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterFarming extends JavaPlugin implements SlimefunAddon {

    public static ItemGroup betterFarming;
    public static SlimefunItemStack goldenAppleHoe;
    public static SlimefunItemStack enchGoldenAppleHoe;
    public static SlimefunItemStack appleHoe;
    public static SlimefunItemStack breakablePot;
    public static SlimefunItemStack greenRupee;
    public static SlimefunItemStack blueRupee;
    public static SlimefunItemStack redRupee;
    public static SlimefunItemStack purpleRupee;
    public static SlimefunItemStack orangeRupee;
    public static SlimefunItemStack silverRupee;
    public static SlimefunItemStack goldRupee;
    public static SlimefunItemStack kokiriSword;
    public static SlimefunItemStack magicalSword;
    public static SlimefunItemStack masterSword;

    private static BetterFarming plugin;

    public static BetterFarming getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        new BlockBreakHandler(this);

        betterFarming = new ItemGroup(
            new NamespacedKey(this, "farming_items"),
            namedItem(Material.WOODEN_HOE, "&6Better Farming")
        );

        appleHoe = new SlimefunItemStack(
            "APPLEHOE", Material.WOODEN_HOE, "&cApple Hoe", "",
            "&7Breaking leaves has an increased chance", "&7to drop an apple."
        );
        goldenAppleHoe = new SlimefunItemStack(
            "GOLDAPPLEHOE", Material.GOLDEN_HOE, "&6Golden Apple Hoe", "",
            "&7Breaking leaves has a chance", "&7to drop a golden apple."
        );
        enchGoldenAppleHoe = new SlimefunItemStack(
            "ENCHGOLDAPPLEHOE", Material.GOLDEN_HOE, "&dEnchanted Golden Apple Hoe", "",
            "&7Breaking leaves has a rare chance", "&7to drop an enchanted golden apple."
        );

        greenRupee = new SlimefunItemStack("GREENRUPEE", Material.EMERALD, "&aGreen Rupee", "");
        blueRupee = new SlimefunItemStack("BLUERUPEE", Material.LAPIS_LAZULI, "&1Blue Rupee", "");
        redRupee = new SlimefunItemStack("REDRUPEE", Material.RED_DYE, "&cRed Rupee", "");
        purpleRupee = new SlimefunItemStack("PURPLERUPEE", Material.PURPLE_DYE, "&5Purple Rupee", "");
        orangeRupee = new SlimefunItemStack("ORANGERUPEE", Material.ORANGE_DYE, "&6Orange Rupee", "");
        silverRupee = new SlimefunItemStack("SILVERRUPEE", Material.LIGHT_GRAY_DYE, "&7Silver Rupee", "");
        goldRupee = new SlimefunItemStack("GOLDRUPEE", Material.GLOWSTONE_DUST, "&eGold Rupee", "");
        breakablePot = new SlimefunItemStack(
            "BREAKABLEPOT", Material.BOWL, "&6Breakable Pot", "", "&fRight-click to receive 4-5 Green Rupees."
        );

        kokiriSword = new SlimefunItemStack(
            "KOKIRISWORD", Material.WOODEN_SWORD, "&aKokiri Sword", "",
            "&aBreaking grass with this sword may", "&adrop Rupees."
        );
        magicalSword = new SlimefunItemStack(
            "MAGICALSWORD", Material.IRON_SWORD, "&fMagical Sword", "",
            "&aBreaking grass with this sword may", "&adrop better Rupees."
        );
        masterSword = new SlimefunItemStack(
            "MASTERSWORD", Material.DIAMOND_SWORD, "&bMaster Sword", "",
            "&aBreaking grass with this sword may", "&adrop higher-tier Rupees.",
            "&aThe chance improves while at full health."
        );

        masterSword.addUnsafeEnchantment(Enchantment.UNBREAKING, 3);
        enchGoldenAppleHoe.addUnsafeEnchantment(Enchantment.UNBREAKING, 5);

        ItemStack[] potRecipe = {
            new ItemStack(Material.BRICKS), new ItemStack(Material.BRICKS), new ItemStack(Material.BRICKS),
            new ItemStack(Material.BRICKS), null, new ItemStack(Material.BRICKS),
            new ItemStack(Material.BRICKS), new ItemStack(Material.BRICKS), new ItemStack(Material.BRICKS)
        };
        ItemStack[] greenRupeeRecipe = {
            new ItemStack(Material.EMERALD), new ItemStack(Material.REDSTONE), null,
            null, null, null,
            null, null, null
        };
        ItemStack[] blueRupeeRecipe = four(greenRupee);
        ItemStack[] redRupeeRecipe = four(blueRupee);
        ItemStack[] purpleRupeeRecipe = four(redRupee);
        ItemStack[] orangeRupeeRecipe = four(purpleRupee);
        ItemStack[] silverRupeeRecipe = four(orangeRupee);
        ItemStack[] goldRupeeRecipe = four(silverRupee);
        ItemStack[] kokiriSwordRecipe = surround(greenRupee, new ItemStack(Material.WOODEN_SWORD));
        ItemStack[] magicalSwordRecipe = surround(redRupee, kokiriSword);
        ItemStack[] masterSwordRecipe = surround(silverRupee, magicalSword);
        ItemStack[] appleHoeRecipe = surround(new ItemStack(Material.APPLE), new ItemStack(Material.WOODEN_HOE));
        ItemStack[] goldenAppleHoeRecipe = surround(new ItemStack(Material.GOLDEN_APPLE), appleHoe);
        ItemStack[] enchGoldenAppleHoeRecipe = surround(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), goldenAppleHoe);

        register(new NotPlaceableItem(betterFarming, greenRupee, RecipeType.SMELTERY, greenRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, blueRupee, RecipeType.ENHANCED_CRAFTING_TABLE, blueRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, redRupee, RecipeType.ENHANCED_CRAFTING_TABLE, redRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, purpleRupee, RecipeType.ENHANCED_CRAFTING_TABLE, purpleRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, orangeRupee, RecipeType.ENHANCED_CRAFTING_TABLE, orangeRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, silverRupee, RecipeType.ENHANCED_CRAFTING_TABLE, silverRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, goldRupee, RecipeType.ENHANCED_CRAFTING_TABLE, goldRupeeRecipe));
        register(new NotPlaceableItem(betterFarming, kokiriSword, RecipeType.ENHANCED_CRAFTING_TABLE, kokiriSwordRecipe));
        register(new NotPlaceableItem(betterFarming, magicalSword, RecipeType.ENHANCED_CRAFTING_TABLE, magicalSwordRecipe));
        register(new NotPlaceableItem(betterFarming, masterSword, RecipeType.ENHANCED_CRAFTING_TABLE, masterSwordRecipe));
        register(new NotPlaceableItem(betterFarming, appleHoe, RecipeType.ENHANCED_CRAFTING_TABLE, appleHoeRecipe));
        register(new NotPlaceableItem(betterFarming, goldenAppleHoe, RecipeType.ENHANCED_CRAFTING_TABLE, goldenAppleHoeRecipe));
        register(new NotPlaceableItem(betterFarming, enchGoldenAppleHoe, RecipeType.ENHANCED_CRAFTING_TABLE, enchGoldenAppleHoeRecipe));
        register(new BreakablePot(betterFarming, breakablePot, RecipeType.SMELTERY, potRecipe));

        new Research(new NamespacedKey(this, "loz_swords"), 425689, "Legendary Swords", 22)
            .addItems(kokiriSword, magicalSword, masterSword).register();
        new Research(new NamespacedKey(this, "breakable_pot"), 425691, "Breakable Pot", 5)
            .addItems(breakablePot).register();
        new Research(new NamespacedKey(this, "rupees"), 425692, "Rupee Economy", 20)
            .addItems(greenRupee, blueRupee, redRupee, purpleRupee, orangeRupee, silverRupee, goldRupee).register();
    }

    private void register(SlimefunItem item) {
        item.register(this);
    }

    private static ItemStack[] four(ItemStack ingredient) {
        return new ItemStack[] {
            ingredient, ingredient, null,
            ingredient, ingredient, null,
            null, null, null
        };
    }

    private static ItemStack[] surround(ItemStack ingredient, ItemStack center) {
        return new ItemStack[] {
            ingredient, ingredient, ingredient,
            ingredient, center, ingredient,
            ingredient, ingredient, ingredient
        };
    }

    private static ItemStack namedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/wickidcow/SF_BetterFarming/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}
