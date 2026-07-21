package com.example.vanillahead;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class VanillaHead extends JavaPlugin {

    private HttpServer statsServer;
    // Порт, на котором плагин будет отдавать данные сайту. 
    // Выбери любой свободный, например 8080 или 9000.
    // ВАЖНО: Этот порт должен быть открыт в фаерволе твоего хостинга!
    private static final int WEB_PORT = 8080; 

    @Override
    public void onEnable() {
        getLogger().info("🟩 VanillaHead enabled!");

        // 1. Регистрируем команду /vanillahead
        PluginCommand cmd = getCommand("vanillahead");
        if (cmd != null) {
            cmd.setExecutor((sender, command, label, args) -> {
                if (sender instanceof Player player) {
                    player.sendMessage(ChatColor.GREEN + "✅ Работает!");
                    return true;
                }
                return true;
            });
        }

        // 2. ЗАПУСКАЕМ СВОЙ СЕРВЕР ДЛЯ САЙТА
        startStatsServer();
    }

    private void startStatsServer() {
        try {
            // Слушаем все интерфейсы (0.0.0.0) на порту WEB_PORT
            statsServer = HttpServer.create(new InetSocketAddress(WEB_PORT), 0);
            statsServer.createContext("/status", new StatsHandler());
            statsServer.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(2));
            statsServer.start();
            getLogger().info("🌐 Статистический сервер запущен на порту " + WEB_PORT);
        } catch (IOException e) {
            getLogger().severe("❌ Не удалось запустить сервер статистики: " + e.getMessage());
        }
    }

    // Обработчик запросов от сайта
    static class StatsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            int online = Bukkit.getOnlinePlayers().size();
            int max = Bukkit.getMaxPlayers();
            
            // Формируем JSON ответ
            String response = String.format("{\"online\": %d, \"max\": %d}", online, max);
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }
    }

    @Override
    public void onDisable() {
        if (statsServer != null) statsServer.stop(0);
        getLogger().info("🟥 VanillaHead disabled!");
    }
}
