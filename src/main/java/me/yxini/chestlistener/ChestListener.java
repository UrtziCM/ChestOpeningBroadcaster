package me.yxini.chestlistener;

import me.yxini.chestlistener.eventlisteners.OpenChestEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChestListener extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("EventListenerTest has started");
        getServer().getPluginManager().registerEvents(new OpenChestEventListener(), this);
    }
}
