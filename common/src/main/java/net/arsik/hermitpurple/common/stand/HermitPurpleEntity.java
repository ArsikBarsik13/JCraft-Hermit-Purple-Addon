package net.arsik.hermitpurple.common.stand;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.arna.jcraft.api.Attacks;
import net.arna.jcraft.api.stand.StandData;
import net.arna.jcraft.api.stand.SummonData;
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
    public static final StandData DATA = StandData.builder()
            .info(StandInfo.builder()
                    .name(Component.literal("Hermit Purple"))
                    .proCount(3)
                    .conCount(2)
                    .build())
            .summonData(SummonData.of(JSoundRegistry.HAMON_RING))
            .build();

    public static final SimpleAttack<HermitPurpleEntity> VINE_WHIP_FOLLOWUP = new SimpleAttack<HermitPurpleEntity>(
            0, 5,7, 2.2f, 0.9f, 20, 1.0f, 0.1f, 0.1f)
            .withImpactSound(JSoundRegistry.IMPACT_2)
            .withInfo(
                    Component.literal("Second hit Vine Whip"),
                    Component.empty()
            );
    //todo: make closer hits so the enemy can't hide in front of you
    public static final SimpleAttack<HermitPurpleEntity> VINE_WHIP = new SimpleAttack<HermitPurpleEntity>(
            2, 6, 8, 2.0f, 0.8f, 20, 0.95f, 0.3f, 0.1f)
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
                    Component.literal("§eHamon Empower (HAMON SPEC-ONLY)§f"),
                    Component.literal("§eEmpower your attacks with Hamon energy§f")
            ); //is an SP barrage atm
    public static final KnockdownAttack<HermitPurpleEntity> HP_KNOCKDOWN = new KnockdownAttack<HermitPurpleEntity>(
            0, 4, 6, 1.35f, 1.1f, 25, 1.3f, 0.4f, 0, 6)
            .withImpactSound(JSoundRegistry.HAMON_CRACKLES) //needs the right sound
            .withHitSpark(JParticleType.HIT_SPARK_1)
            .withLaunch()
            .withInfo(
                    Component.literal("Hermit Barrage(Finisher)"),
                    Component.empty()
            );
    public static final SimpleMultiHitAttack<HermitPurpleEntity> HERMIT_BARRAGE = new SimpleMultiHitAttack<HermitPurpleEntity>(
            0, 26, 1.40f, 0.9f, 25, 1.1f, 0.3f, 0.2f, IntSet.of(6, 14))
            .withFinisher(20, HP_KNOCKDOWN)
            .withCrouchingVariant(HAMON_CHARGE)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withInfo(
                    Component.literal("Hermit Barrage"),
                    Component.literal("Hermit Purple does A 3 Hit Combo, last hit knocks down")
            );
    public static final SimpleUppercutAttack<HermitPurpleEntity> UPWARD_LAUNCH = new SimpleUppercutAttack<HermitPurpleEntity>(
            7, 12, 20, 1.75f, 1.2f, 32, 1.5f, 1.1f, 0.1f, 0.2f)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withLaunch()
            .withInfo(
                    Component.literal("Upward Launch"),
                    Component.literal("Hermit Purple launches the enemy upwards and stuns them")
            );
    public static final KnockdownAttack<HermitPurpleEntity> SLAM = new KnockdownAttack<HermitPurpleEntity>(
            8, 12, 24, 1.75f, 1.2f, 30, 1.5f, 1.1f, 0.1f, 5)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withCrouchingVariant(UPWARD_LAUNCH)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withInfo(
                    Component.literal("Slam"),
                    Component.literal("Hermit Purple swings its branch downwards")
            );
    //todo: make a closer hit
    public static final SimpleAttack<HermitPurpleEntity> HP_GETOUT2 = new SimpleAttack<HermitPurpleEntity>(
            0, 0, 2, 1.0f, 0.8f, 24, 0.7f, 1.6f, 0.1f)
            .withSound(JSoundRegistry.IMPACT_5)
            .withInfo(
                    Component.literal("HP_GETOUT2"),
                    Component.literal("")
            );
    public static final SimpleMultiHitAttack<HermitPurpleEntity> GET_OUT = new SimpleMultiHitAttack<HermitPurpleEntity>(
            360, 7, 1.7f, 0.8f, 24, 0.9f, 1.6f, 0.1f, IntSet.of(2, 3))
            .withFinisher(5, HP_GETOUT2)
            .withSound(JSoundRegistry.IMPACT_3)
            .withInfo(
                    Component.literal("Get Out!"),
                    Component.literal("Hermit Purple does a swipe that knocks enemies away")
            );

    public HermitPurpleEntity(Level world) {
        super(StandTypeRegistry.HERMIT_PURPLE.get(), world);
    }

    private static void registerMoves(MoveMap<HermitPurpleEntity, State> moves) {
        var light = moves.register(MoveClass.LIGHT, VINE_WHIP, HermitPurpleEntity.State.LIGHT);
        light.withFollowup(State.LIGHT_FOLLOWUP);

        moves.register(MoveClass.BARRAGE, HERMIT_BARRAGE, State.BARRAGE);

        moves.register(MoveClass.HEAVY, SLAM, State.BARRAGE);

        moves.register(MoveClass.SPECIAL2, GET_OUT, State.BARRAGE);
    } //redo State.whatever after we get animations

    @Override
    public boolean initMove(final MoveClass moveClass) {
        if (tryFollowUp(moveClass, MoveClass.LIGHT)) return true;
        return super.initMove(moveClass);
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
        LIGHT_FOLLOWUP(Attacks.createAnimationCommand(JCraft.BASE_CONTROLLER, "hermit_purple.light_followup", AzPlayBehaviors.HOLD_ON_LAST_FRAME)),
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
