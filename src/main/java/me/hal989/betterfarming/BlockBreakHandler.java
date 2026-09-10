package me.hal989.betterfarming;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static me.hal989.betterfarming.BetterFarming.*;

public class BlockBreakHandler implements Listener {

    private static final Set<Material> LEAVES = materials(
        "OAK_LEAVES", "DARK_OAK_LEAVES", "SPRUCE_LEAVES", "BIRCH_LEAVES",
        "ACACIA_LEAVES", "JUNGLE_LEAVES", "MANGROVE_LEAVES", "CHERRY_LEAVES",
        "AZALEA_LEAVES", "FLOWERING_AZALEA_LEAVES", "PALE_OAK_LEAVES"
    );

    private static final Set<Material> SEARCHABLE_VEGETATION = materials(
        "SHORT_GRASS", "TALL_GRASS",
        "SHORT_DRY_GRASS", "TALL_DRY_GRASS",
        "BUSH", "FIREFLY_BUSH", "WILDFLOWERS", "CACTUS_FLOWER", "LEAF_LITTER"
    );

    public BlockBreakHandler(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();
        SlimefunItem hand = SlimefunItem.getByItem(player.getInventory().getItemInMainHand());
        if (hand == null) {
            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        String id = hand.getId();

        if (LEAVES.contains(material)) {
            if (id.equals(appleHoe.getItemId()) && random.nextDouble() < 0.30) {
                drop(block, new ItemStack(Material.APPLE));
            } else if (id.equals(goldenAppleHoe.getItemId()) && random.nextDouble() < 0.10) {
                drop(block, new ItemStack(Material.GOLDEN_APPLE));
            } else if (id.equals(enchGoldenAppleHoe.getItemId()) && random.nextDouble() < 0.05) {
                drop(block, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
            }
            return;
        }

        if (!SEARCHABLE_VEGETATION.contains(material)) {
            return;
        }

        if (id.equals(kokiriSword.getItemId()) && random.nextDouble() > 0.50) {
            player.getInventory().addItem((random.nextDouble() < 0.90 ? greenRupee : blueRupee).clone());
        } else if (id.equals(copperRupeeSword.getItemId()) && random.nextDouble() > 0.45) {
            drop(block, (random.nextDouble() < 0.80 ? greenRupee : blueRupee).clone());
        } else if (id.equals(copperRupeeSpear.getItemId()) && random.nextDouble() > 0.40) {
            drop(block, (random.nextDouble() < 0.85 ? blueRupee : redRupee).clone());
        } else if (id.equals(magicalSword.getItemId()) && random.nextDouble() > 0.30) {
            drop(block, (random.nextDouble() < 0.90 ? blueRupee : redRupee).clone());
        } else if (id.equals(masterSword.getItemId())) {
            AttributeInstance maxHealthAttribute = player.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH);
            double maxHealth = maxHealthAttribute == null ? 20.0 : maxHealthAttribute.getValue();
            boolean fullHealth = player.getHealth() >= maxHealth - 0.001;
            if (random.nextDouble() > 0.30 || fullHealth) {
                drop(block, (random.nextDouble() < 0.90 ? redRupee : purpleRupee).clone());
            } else if (random.nextDouble() < 0.50) {
                drop(block, redRupee.clone());
            }
        }
    }

    private static Set<Material> materials(String... names) {
        EnumSet<Material> materials = EnumSet.noneOf(Material.class);
        for (String name : names) {
            Material material = Material.getMaterial(name);
            if (material != null) {
                materials.add(material);
            }
        }
        return Collections.unmodifiableSet(materials);
    }

    private static void drop(Block block, ItemStack stack) {
        block.getWorld().dropItemNaturally(block.getLocation(), stack);
    }
}
