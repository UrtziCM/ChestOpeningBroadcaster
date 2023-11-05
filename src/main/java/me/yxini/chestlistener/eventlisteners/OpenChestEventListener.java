package me.yxini.chestlistener.eventlisteners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/**
 * Broadcasts to online operators that a chest has been opened. Allowing to fast TP clicking the message.
 *
 * @author Yxini
 * @version 1.0
 */
public class OpenChestEventListener implements Listener {
    /**
     * When a player opens a chest this event is triggered.
     *
     * @param event The event that triggers after right-clicking a block.
     */
    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        /* The block that was just clicked */
        Block clickedBlock = event.getClickedBlock();
        assert clickedBlock != null; // Make sure we dont get null blocks.
        if (clickedBlock.getType() == Material.CHEST && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { // Check if the block was a chest and if it was right-clicked
            /* Get the block location to TP later*/
            Location location = clickedBlock.getLocation().add(new Vector(0.5,1,0.5)); // Center player and set it on top of the chest.
            /* Prepare the text to be shown */
            TextComponent tc = getOpenTextMessage(clickedBlock);
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Go"))); // Text to be shown on hover
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @s "
                    + location.getX() + " " + location.getY() + " " + location.getZ() + " 0 90")); // On click throw TP command
            /* Send the message only to server operators */
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isOp())
                    player.spigot().sendMessage(tc);
            }
        }
    }

    /**
     *
     * @param clickedBlock The chest that a player interacted with.
     * @return Colored and formatted TextComponent with chest coordinates
     */
    private static TextComponent getOpenTextMessage(Block clickedBlock) {
        String chestMessage = ChatColor.AQUA + "[Chest Notifier]"+ ChatColor.GRAY +" A chest has been opened at: [ "
                + ChatColor.DARK_RED + clickedBlock.getLocation().getX() + ChatColor.GRAY + ", "
                + ChatColor.DARK_GREEN + clickedBlock.getLocation().getY() + ChatColor.GRAY + ", "
                + ChatColor.DARK_BLUE + clickedBlock.getLocation().getZ() + ChatColor.GRAY + " ]";
        return new TextComponent(chestMessage);
    }
}
