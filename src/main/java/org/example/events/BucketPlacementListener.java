package org.example.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import org.jspecify.annotations.NonNull;

public final class BucketPlacementListener implements EventListener<PlayerUseItemOnBlockEvent> {

    @Override
    public @NonNull Class<PlayerUseItemOnBlockEvent> eventType() {
        return PlayerUseItemOnBlockEvent.class;
    }

    @Override
    public EventListener.@NonNull Result run(PlayerUseItemOnBlockEvent event) {
        Material material = event.getItemStack().material();
        if (material != Material.WATER_BUCKET && material != Material.LAVA_BUCKET) {
            return EventListener.Result.SUCCESS;
        }

        Player player = event.getPlayer();
        if (player.getInstance() == null) return EventListener.Result.SUCCESS;

        Block fluid = material == Material.WATER_BUCKET ? Block.WATER : Block.LAVA;
        var placePos = event.getPosition().relative(event.getBlockFace()).asBlockVec();
        player.getInstance().setBlock(placePos, fluid);

        return EventListener.Result.SUCCESS;
    }
}
