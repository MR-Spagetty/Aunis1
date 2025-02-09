package tauri.dev.jsg.block.stargate;

import tauri.dev.jsg.JSG;
import tauri.dev.jsg.util.main.JSGProps;
import tauri.dev.jsg.gui.GuiIdEnum;
import tauri.dev.jsg.stargate.CamoPropertiesHelper;
import tauri.dev.jsg.stargate.EnumMemberVariant;
import tauri.dev.jsg.tileentity.stargate.StargateClassicBaseTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class StargateClassicBaseBlock extends StargateAbstractBaseBlock {
	
	public StargateClassicBaseBlock(String blockName) {
		super(blockName);
	}
	
	// --------------------------------------------------------------------------------------
	// Interactions
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		StargateClassicBaseTile gateTile = (StargateClassicBaseTile) world.getTileEntity(pos);
		EnumFacing facing = placer.getHorizontalFacing().getOpposite();
		
		if (!world.isRemote) {			
			state = state.withProperty(JSGProps.FACING_HORIZONTAL, facing)
					.withProperty(JSGProps.RENDER_BLOCK, true);
		
			world.setBlockState(pos, state);
					
			gateTile.updateFacing(facing, true);
			gateTile.updateMergeState(gateTile.getMergeHelper().checkBlocks(world, pos, facing), facing);
		}
	}

	@Override
	protected void showGateInfo(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
		StargateClassicBaseTile tile = (StargateClassicBaseTile) world.getTileEntity(pos);
		if(tile != null && tile.isMerged() && !tile.tryInsertUpgrade(player, hand)) {
			player.openGui(JSG.instance, GuiIdEnum.GUI_STARGATE.id, world, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	@Override
	protected IBlockState createMemberState(IBlockState memberState, EnumFacing facing, int meta) {
		return memberState.withProperty(JSGProps.RENDER_BLOCK, true)
				.withProperty(JSGProps.FACING_HORIZONTAL, facing)
				.withProperty(JSGProps.MEMBER_VARIANT, EnumMemberVariant.byId((meta >> 3) & 0x01));
	}

	// --------------------------------------------------------------------------------------
	// Rendering
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return CamoPropertiesHelper.getStargateBlockBoundingBox(state, access, pos, false);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return CamoPropertiesHelper.getStargateBlockBoundingBox(state, access, pos, true);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}
}
