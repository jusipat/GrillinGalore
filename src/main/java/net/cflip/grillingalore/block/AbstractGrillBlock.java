package net.cflip.grillingalore.block;

import net.cflip.grillingalore.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractGrillBlock extends BlockWithEntity {
	public static final BooleanProperty LIT = Properties.LIT;

	public AbstractGrillBlock(Settings settings) {
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(LIT, false));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (state.get(LIT) && !entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
			entity.damage(DamageSource.HOT_FLOOR, 1.0f);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	protected abstract void openScreen(World world, BlockPos pos, PlayerEntity player);

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			openScreen(world, pos, player);
			return ActionResult.CONSUME;
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(LIT) && world.getBlockState(pos.up()).isOf(ModBlocks.RAW_RIBS)) {
			world.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
			world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
			world.addParticle(ParticleTypes.LAVA, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

}
