package org.example.events;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.entitites.CustomZombie;
import org.jspecify.annotations.NonNull;

public final class PlayerSpawnListener implements EventListener<PlayerSpawnEvent> {

    @Override
    public @NonNull Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }

    @Override
    public @NonNull Result run(PlayerSpawnEvent event) {
        if (!event.isFirstSpawn()) {
            return Result.SUCCESS;
        }

        Player player = event.getPlayer();
        var facing = player.getPosition().facing();
        Pos spawnPos = player.getPosition().add(facing.normalX() * 1.5, 0, facing.normalZ() * 1.5);

        CustomZombie zombie = new CustomZombie();
        zombie.setInstance(player.getInstance(), spawnPos);

        return Result.SUCCESS;
    }
}
