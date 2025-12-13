package org.example;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import org.example.commands.BenchmarkCommand;
import org.example.commands.GamemodeCommand;
import org.example.events.BucketPlacementListener;
import org.example.events.PlayerSpawnListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setGenerator(generationUnit -> generationUnit.modifier().fillHeight(0, 2, Block.GRASS_BLOCK));
        instanceContainer.setChunkSupplier(LightingChunk::new);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 4, 0));
        });

        globalEventHandler.addListener(new BucketPlacementListener());
        globalEventHandler.addListener(new PlayerSpawnListener());

        MinecraftServer.getCommandManager().register(new BenchmarkCommand());
        MinecraftServer.getCommandManager().register(new GamemodeCommand());

        LOGGER.info("Starting Minestom server on 0.0.0.0:25565");
        minecraftServer.start("0.0.0.0", 25565);
    }
}
