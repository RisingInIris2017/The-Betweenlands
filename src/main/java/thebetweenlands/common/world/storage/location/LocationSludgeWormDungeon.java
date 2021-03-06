package thebetweenlands.common.world.storage.location;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.api.storage.IWorldStorage;
import thebetweenlands.api.storage.LocalRegion;
import thebetweenlands.api.storage.StorageID;
import thebetweenlands.client.render.shader.postprocessing.GroundFog.GroundFogVolume;
import thebetweenlands.client.render.shader.postprocessing.WorldShader;
import thebetweenlands.common.network.datamanager.GenericDataManager;

public class LocationSludgeWormDungeon extends LocationGuarded {
	protected static final DataParameter<Float> GROUND_FOG_STRENGTH = GenericDataManager.createKey(LocationSludgeWormDungeon.class, DataSerializers.FLOAT);

	private BlockPos structurePos;

	public LocationSludgeWormDungeon(IWorldStorage worldStorage, StorageID id, @Nullable LocalRegion region) {
		super(worldStorage, id, region, "sludge_worm_dungeon", EnumLocationType.DUNGEON);
		this.dataManager.register(GROUND_FOG_STRENGTH, 1.0F);
	}

	/**
	 * Sets the structure entrance
	 * @param pos
	 */
	public void setStructurePos(BlockPos pos) {
		this.structurePos = pos;
		this.setDirty(true);
	}

	/**
	 * Returns the structure entrance
	 * @return
	 */
	public BlockPos getStructurePos() {
		return this.structurePos;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("groundFogStrength", this.dataManager.get(GROUND_FOG_STRENGTH));
		nbt.setLong("structurePos", this.structurePos.toLong());
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.dataManager.set(GROUND_FOG_STRENGTH, nbt.getFloat("groundFogStrength"));
		this.structurePos = BlockPos.fromLong(nbt.getLong("structurePos"));
	}

	@Override
	protected void writeSharedNbt(NBTTagCompound nbt) {
		super.writeSharedNbt(nbt);
		nbt.setLong("structurePos", this.structurePos.toLong());
	}
	
	@Override
	protected void readSharedNbt(NBTTagCompound nbt) {
		super.readSharedNbt(nbt);
		this.structurePos = BlockPos.fromLong(nbt.getLong("structurePos"));
	}
	
	@Override
	public void update() {
		super.update();

		//TODO Clear fog strength when dungeon is conquered
	}

	@SideOnly(Side.CLIENT)
	public boolean addGroundFogVolumesToShader(WorldShader shader) {
		float strength = this.dataManager.get(GROUND_FOG_STRENGTH);

		if(strength > 0) {
			for(int floor = 0; floor < 7; floor++) {
				shader.addGroundFogVolume(new GroundFogVolume(new Vec3d(this.structurePos.getX(), this.structurePos.getY() - 5.2D - floor * 6, this.structurePos.getZ()), new Vec3d(29, 6, 29), 0.035F, 6.0F, 0.5F * strength, 0.5F * strength, 0.5F * strength));
			}

			return true;
		}

		return false;
	}
}
