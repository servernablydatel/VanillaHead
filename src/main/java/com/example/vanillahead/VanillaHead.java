package com.example.vanillahead;

import org.bukkit.plugin.java.JavaPlugin;

public class VanillaHead extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("VanillaHead plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("VanillaHead plugin disabled!");
    }
}
