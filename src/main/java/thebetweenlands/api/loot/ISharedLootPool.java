package thebetweenlands.api.loot;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

public interface ISharedLootPool {
	/**
	 * Returns the loot table location that generates the shared
	 * loot pool's items. May return null if the shared loot pool
	 * is not generated by a loot table or if the shared loot pool is a combined
	 * view of multiple shared loot pools.
	 * @return
	 */
	@Nullable
	public ResourceLocation getLootTable();

	/**
	 * Returns a loot table view of this shared loot pool
	 * that allows retrieving items from the shared loot pool.
	 * Note that the loot table view will always be frozen and
	 * not have any pools. Also keep in mind that every time loot
	 * is generated from this loot table it is removed from
	 * the shared pool!
	 * @return
	 */
	public LootTableView getLootTableView();

	/**
	 * Returns how many items of the specified loot entry in the
	 * specified loot pool and roll have already been removed from the shared loot pool.
	 * @param pool The loot pool
	 * @param poolRoll The pool roll
	 * @param entry The loot entry
	 * @return
	 */
	public int getRemovedItems(String pool, int poolRoll, String entry);

	/**
	 * Sets how many items of the specified loot entry in the
	 * specified loot pool and roll have already been removed from the shared loot pool.
	 * @param pool The loot pool
	 * @param poolRoll The pool roll
	 * @param entry The loot entry
	 * @param count How many items have already been removed
	 */
	public void setRemovedItems(String pool, int poolRoll, String entry, int count);

	/**
	 * Returns a seed for the specified loot pool and roll
	 * @param rand RNG used to generate the seed if it doesn't exist yet
	 * @param pool The loot pool
	 * @param poolRoll The loot roll. Use negative values for pool seeds
	 * that aren't for pool rolls but only for the pool
	 * @return
	 */
	public long getLootPoolSeed(Random rand, String pool, int poolRoll);

	/**
	 * Returns a see for the specified loot entry in the specified loot pool and roll
	 * @param rand RNG used to generate the seed if it doesn't exist yet
	 * @param pool The loot pool
	 * @param poolRoll The loot roll
	 * @param entry The loot entry
	 * @return
	 */
	public long getLootEntrySeed(Random rand, String pool, int poolRoll, String entry);

	/**
	 * Combines this shared loot pool with another shared loot pool
	 * and returns a new <b>unmodifiable shared loot pool view</b> that combines both
	 * shared loot pools. The returned shared loot pool does not hold any information
	 * itself.
	 * @param other Other shared loot pool to combine with this shared loot pool
	 * @return
	 */
	public ISharedLootPool combine(ISharedLootPool other);

	/**
	 * Regenerates the shared loot pool and discards
	 * any loot seeds, i.e. the new loot will not be
	 * the same anymore
	 */
	public void regenerate();

	/**
	 * Refills the shared loot pool without discarding
	 * any loot seeds, i.e. the loot will be the same
	 * but is completely refilled
	 */
	public void refill();
}
