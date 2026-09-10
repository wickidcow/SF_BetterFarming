package me.hal989.betterfarming;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static me.hal989.betterfarming.BetterFarming.*;

public class BlockBreakHandler implements Listener {

    private static final Set<Material> SEARCHABLE_VEGETATION = materials(
        "SHORT_GRASS", "TALL_GRASS", "SHORT_DRY_GRASS", "TALL_DRY_GRASS",
        "BUSH", "FIREFLY_BUSH", "WILDFLOWERS", "CACTUS_FLOWER", "LEAF_LITTER"
    );

    private final BetterFarming plugin;

    public BlockBreakHandler(BetterFarming plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
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

        if (Tag.LEAVES.isTagged(material)) {
            if (id.equals(appleHoe.getItemId()) && roll(random, "drops.apple-hoe", 30.0)) {
                drop(block, new ItemStack(Material.APPLE));
            } else if (id.equals(goldenAppleHoe.getItemId()) && roll(random, "drops.golden-apple-hoe", 10.0)) {
                drop(block, new ItemStack(Material.GOLDEN_APPLE));
            } else if (id.equals(enchGoldenAppleHoe.getItemId()) && roll(random, "drops.enchanted-golden-apple-hoe", 5.0)) {
                drop(block, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
            }
            return;
        }

        if (!SEARCHABLE_VEGETATION.contains(material)) {
            return;
        }

        if (id.equals(kokiriSword.getItemId()) && roll(random, "drops.kokiri-sword", 50.0)) {
            player.getInventory().addItem((random.nextDouble() < 0.90 ? greenRupee : blueRupee).clone());
        } else if (id.equals(copperRupeeSword.getItemId()) && roll(random, "drops.copper-rupee-sword", 55.0)) {
            drop(block, (random.nextDouble() < 0.80 ? greenRupee : blueRupee).clone());
        } else if (id.equals(copperRupeeSpear.getItemId()) && roll(random, "drops.copper-rupee-spear", 60.0)) {
            drop(block, (random.nextDouble() < 0.85 ? blueRupee : redRupee).clone());
        } else if (id.equals(magicalSword.getItemId()) && roll(random, "drops.magical-sword", 70.0)) {
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

    private boolean roll(ThreadLocalRandom random, String path, double defaultPercent) {
        double percent = Math.max(0.0, Math.min(100.0, plugin.getConfig().getDouble(path, defaultPercent)));
        return random.nextDouble(100.0) < percent;
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
