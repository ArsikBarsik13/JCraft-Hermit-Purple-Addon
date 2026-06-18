package net.arsik.hermitpurple.common.stand;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.arna.jcraft.api.Attacks;
import net.arna.jcraft.common.attack.moves.shared.*;
import net.arna.jcraft.common.util.JParticleType;
import net.arsik.hermitpurple.common.register.StandTypeRegistry;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.api.attack.MoveMap;
import net.arna.jcraft.api.attack.MoveSet;
import net.arna.jcraft.api.attack.MoveSetManager;
import net.arna.jcraft.api.attack.enums.MoveClass;
import net.arna.jcraft.api.registry.JSoundRegistry;
import net.arna.jcraft.api.stand.StandData;
import net.arna.jcraft.api.stand.StandEntity;
import net.arna.jcraft.api.stand.StandInfo;
import net.arna.jcraft.common.util.StandAnimationState;
import net.arsik.hermitpurple.HermitPurple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class HermitPurpleEntity extends StandEntity<HermitPurpleEntity, HermitPurpleEntity.State> {
    public static final MoveSet<HermitPurpleEntity, State> MOVE_SET = MoveSetManager.create(StandTypeRegistry.HERMIT_PURPLE,
            HermitPurpleEntity::registerMoves, State.class);
    public static final StandData DATA = StandData.of(StandInfo.of(Component.literal("Hermit Purple")));

    public static final SimpleAttack<HermitPurpleEntity> VINE_WHIP_FOLLOWUP = new SimpleAttack<HermitPurpleEntity>(
            0, 4,2 , 2.2f, 0.6f, 3, 0.8f, 0.1f, 0.2f)
            .withImpactSound(JSoundRegistry.IMPACT_3)
            .withInfo(
                    Component.literal("Second hit Vine Whip"),
                    Component.empty()
            );
    public static final SimpleAttack<HermitPurpleEntity> VINE_WHIP = new SimpleAttack<HermitPurpleEntity>(
            40, 1, 2, 2.0f, 0.5f, 3, 0.8f, 0.2f, 0.0f)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withFollowup(VINE_WHIP_FOLLOWUP)
            .withInfo(
                    Component.literal("Vine Whip"),
                    Component.literal("Semi-quick combo starter/extender, goes in a series of two with a criss-cross pattern")
            );
    public static final MainBarrageAttack<HermitPurpleEntity> HAMON_CHARGE = new MainBarrageAttack<HermitPurpleEntity>(280,
            0, 40, 0.75f, 1f, 30, 2f, 0.25f, 0f, 3, Blocks.OBSIDIAN.defaultDestroyTime())
            .withSound(JSoundRegistry.HAMON_SURGE)
            .withInfo(
                    Component.translatable("move.jcraft.hamon.default.empower_move.name"),
                    Component.translatable("move.jcraft.hamon.default.empower_move.description")
            );
    public static final KnockdownAttack<HermitPurpleEntity> HP_KNOCKDOWN = new KnockdownAttack<HermitPurpleEntity>(
            0, 2, 4, 1.15f, 0.8f, 16, 1.3f, 0.4f, 0, 10)
            .withImpactSound(JSoundRegistry.TW_KICK_HIT)
            .withHitSpark(JParticleType.HIT_SPARK_1)
            .withLaunch()
            .withInfo(
                    Component.literal("Hermit Barrage(Finisher)"),
                    Component.empty()
            );
    public static final SimpleMultiHitAttack<HermitPurpleEntity> HERMIT_BARRAGE = new SimpleMultiHitAttack<HermitPurpleEntity>(
            0, 25, 1.10f, 0.6f, 7, 1.1f, 0.35f, 0.2f, IntSet.of(1, 1))
            .withFinisher(18, HP_KNOCKDOWN)
            .withCrouchingVariant(HAMON_CHARGE)
            .withSound(JSoundRegistry.HAMON_ECHO)
            .withInfo(
                    Component.literal("Hermit Barrage"),
                    Component.literal("Hermit Purple does A 3 Hit Combo, last hit knocks down")
            );
    public static final SimpleUppercutAttack<HermitPurpleEntity> UPWARD_LAUNCH = new SimpleUppercutAttack<HermitPurpleEntity>(
            7, 10, 20, 1.75f, 0.7f, 15, 1.5f, 1.1f, 0.3f, 35)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withLaunch()
            .withInfo(
                    Component.literal("Upward Launch"),
                    Component.literal("Hermit Purple launches the enemy upwards and stuns them")
            );
    public static final KnockdownAttack<HermitPurpleEntity> SLAM = new KnockdownAttack<HermitPurpleEntity>(
            8, 10, 24, 1.75f, 0.7f, 12, 1.5f, 1.1f, 0.1f, 1)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withCrouchingVariant(UPWARD_LAUNCH)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withInfo(
                    Component.literal("Slam"),
                    Component.literal("Hermit Purple swings its branch downwards")
            );

    public HermitPurpleEntity(Level world) {
        super(StandTypeRegistry.HERMIT_PURPLE.get(), world);
    }

    private static void registerMoves(MoveMap<HermitPurpleEntity, State> moves) {
        var light = moves.register(MoveClass.LIGHT, VINE_WHIP, HermitPurpleEntity.State.LIGHT);
        light.withFollowup(State.LIGHT_FOLLOWUP);

        moves.register(MoveClass.BARRAGE, HERMIT_BARRAGE, State.BARRAGE);

        moves.register(MoveClass.HEAVY, SLAM, State.BARRAGE);
    }

    @NotNull
    @Override
    public HermitPurpleEntity getThis() {
        return this;
    }

    @Override
    protected State[] getStateValues() {
        return State.values();
    }

    @Override
    public State getBlockState() {
        return State.BLOCK;
    }

    public enum State implements StandAnimationState<HermitPurpleEntity> {
        IDLE(AzCommand.create(HermitPurple.BASE_CONTROLLER, "idle", AzPlayBehaviors.LOOP)),
        LIGHT(AzCommand.create(HermitPurple.BASE_CONTROLLER, "hermit_purple.light", AzPlayBehaviors.HOLD_ON_LAST_FRAME)),
        LIGHT_FOLLOWUP(Attacks.createAnimationCommand(JCraft.BASE_CONTROLLER, "", AzPlayBehaviors.HOLD_ON_LAST_FRAME)),
        BLOCK(AzCommand.create(HermitPurple.BASE_CONTROLLER, "block", AzPlayBehaviors.LOOP)),
        BARRAGE(AzCommand.create(HermitPurple.BASE_CONTROLLER, "barrage", AzPlayBehaviors.LOOP));

        private final AzCommand animator;

        State(AzCommand animator) {
            this.animator = animator;
        }

        @Override
        public void playAnimation(HermitPurpleEntity attacker) {
            animator.sendForEntity(attacker);
        }
    }
}
