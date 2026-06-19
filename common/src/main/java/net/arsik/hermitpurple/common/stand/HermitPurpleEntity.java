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
            .withExtraHitBox(-0.65, 0.0f, 1.2f)
            .withExtraHitBox(0.75, 0.0f, 1.1f)
            .withInfo(
                    Component.literal("Second hit Vine Whip"),
                    Component.empty()
            ); //todo: make a cris-cross pattern here
    public static final SimpleAttack<HermitPurpleEntity> VINE_WHIP = new SimpleAttack<HermitPurpleEntity>(
            30, 6, 8, 2.0f, 0.8f, 20, 0.95f, 0.3f, 0.0f)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withFollowup(VINE_WHIP_FOLLOWUP)
            .withExtraHitBox(-0.6, 0.0f, 1.2f)
            .withExtraHitBox(0.7, 0.0f, 1.1f)
            .withInfo(
                    Component.literal("Vine Whip"),
                    Component.literal("Semi-quick combo starter/extender, follows up in a cris-cross pattern")
            );
    public static final MainBarrageAttack<HermitPurpleEntity> HAMON_CHARGE = new MainBarrageAttack<HermitPurpleEntity>(280,
            0, 40, 0.75f, 1f, 30, 2f, 0.25f, 0.0f, 3, Blocks.OBSIDIAN.defaultDestroyTime())
            .withSound(JSoundRegistry.HAMON_SURGE)
            .withInfo(
                    Component.literal("§eHamon Empower (HAMON SPEC-ONLY)§f"),
                    Component.literal("§eEmpower your attacks with Hamon energy§f")
            ); //is an SP barrage atm
    public static final KnockdownAttack<HermitPurpleEntity> HP_KNOCKDOWN = new KnockdownAttack<HermitPurpleEntity>(
            0, 4, 6, 1.35f, 1.1f, 25, 1.3f, 0.4f, 0.0f, 6)
            .withImpactSound(JSoundRegistry.HAMON_CRACKLES) //needs the right sound
            .withHitSpark(JParticleType.HIT_SPARK_1)
            .withExtraHitBox(0.1, 0.0f, 1.2f)
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
            .withExtraHitBox(0.07, 0.0f, 1.0f)
            .withInfo(
                    Component.literal("Hermit Barrage"),
                    Component.literal("Hermit Purple does A 3 Hit Combo, last hit knocks down")
            );
    public static final SimpleUppercutAttack<HermitPurpleEntity> UPWARD_LAUNCH = new SimpleUppercutAttack<HermitPurpleEntity>(
            7, 12, 20, 1.75f, 1.2f, 32, 1.5f, 0.3f, 0.0f, 0.4f)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withExtraHitBox(0.5, 0.0f, 1.2f)
            .withExtraHitBox(-0.4, 0.0f, 0.9f)
            .withLaunch()
            .withInfo(
                    Component.literal("Upward Launch"),
                    Component.literal("Hermit Purple launches the enemy upwards and stuns them")
            );
    public static final KnockdownAttack<HermitPurpleEntity> SLAM = new KnockdownAttack<HermitPurpleEntity>(
            8, 12, 24, 1.75f, 1.2f, 30, 1.5f, 1.1f, 0.0f, 10)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withCrouchingVariant(UPWARD_LAUNCH)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withExtraHitBox(0.5, 0.0f, 1.2f)
            .withExtraHitBox(-0.4, 0.0f, 0.9f)
            .withInfo(
                    Component.literal("Slam"),
                    Component.literal("Hermit Purple swings its branch downwards")
            );
    public static final SimpleAttack<HermitPurpleEntity> GET_OUT = new SimpleAttack<HermitPurpleEntity>(
            360,4, 7, 1.7f, 0.8f, 24, 1.1f, 1.6f, 0.0f)
            .withSound(JSoundRegistry.IMPACT_3)
            .withExtraHitBox(0.8, 0.0f, 1.0f)
            .withExtraHitBox(-0.1, 0.0f, 0.7f)
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
