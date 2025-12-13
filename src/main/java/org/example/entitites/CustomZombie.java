package org.example.entitites;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.attribute.Attribute;

public class CustomZombie extends EntityCreature {

    public CustomZombie() {
        super(EntityType.ZOMBIE);

        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);

        addAIGroup(
                new EntityAIGroupBuilder()
                        .addGoalSelector(new RandomLookAroundGoal(this, 20))
                        .addGoalSelector(new RandomStrollGoal(this, 6))
                        .build()
        );
    }
}
