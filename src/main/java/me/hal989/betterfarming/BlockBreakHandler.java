package me.hal989.betterfarming;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static me.hal989.betterfarming.BetterFarming.*;

public class BlockBreakHandler implements Listener {

    private static final Set<Material> LEAVES = EnumSet.of(
        Material.OAK_LEAVES,
        Material.DARK_OAK_LEAVES,
        Material.SPRUCE_LEAVES,
        Material.BIRCH_LEAVES,
        Material.ACACIA_LEAVES,
        Material.JUNGLE_LEAVES,
        Material.MANGROVE_LEAVES,
        Material.CHERRY_LEAVES,
        Material.AZALEA_LEAVES,
        Material.FLOWERING_AZALEA_LEAVES,
        Material.PALE_OAK_LEAVES
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

        if (material != Material.SHORT_GRASS && material != Material.TALL_GRASS) {
            return;
        }

        if (id.equals(kokiriSword.getItemId()) && random.nextDouble() > 0.50) {
            player.getInventory().addItem((random.nextDouble() < 0.90 ? greenRupee : blueRupee).clone());
        } else if (id.equals(magicalSword.getItemId()) && random.nextDouble() > 0.30) {
            drop(block, (random.nextDouble() < 0.90 ? blueRupee : redRupee).clone());
        } else if (id.equals(masterSword.getItemId())) {
            double maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH) == null
                ? 20.0
                : player.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
            boolean fullHealth = player.getHealth() >= maxHealth - 0.001;
            if (random.nextDouble() > 0.30 || fullHealth) {
                drop(block, (random.nextDouble() < 0.90 ? redRupee : purpleRupee).clone());
            } else if (random.nextDouble() < 0.50) {
                drop(block, redRupee.clone());
            }
        }
    }

    private static void drop(Block block, ItemStack stack) {
        block.getWorld().dropItemNaturally(block.getLocation(), stack);
    }
}
