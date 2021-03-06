package thebetweenlands.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import thebetweenlands.client.tab.BLCreativeTabs;
import thebetweenlands.common.entity.EntityGrapplingHookNode;

public class ItemGrapplingHook extends Item {
	public ItemGrapplingHook() {
		this.setCreativeTab(BLCreativeTabs.ITEMS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) {
			Vec3d dir = player.getLookVec();

			EntityGrapplingHookNode mountNode = new EntityGrapplingHookNode(world);
			mountNode.setLocationAndAngles(player.posX - player.width / 2, player.posY, player.posZ - player.width / 2, 0, 0);
			mountNode.motionX = player.motionX;
			mountNode.motionY = player.motionY;
			mountNode.motionZ = player.motionZ;
			
			player.startRiding(mountNode);
			
			int nodes = 24;
			
			EntityGrapplingHookNode prevNode = null;
			
			for(int i = 0; i < nodes; i++) {
				EntityGrapplingHookNode node = new EntityGrapplingHookNode(world);
				node.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, 0, 0);
				
				float velocity = 1.0F * (0.15F + i / (float)nodes * 0.85F);
				float upwardsVelocity = 1.0F * (float) Math.sin(Math.PI / 2 / nodes * i);
				
				node.motionX = player.motionX + dir.x * velocity;
				node.motionY = player.motionY + dir.y * velocity + upwardsVelocity + 0.5D;
				node.motionZ = player.motionZ + dir.z * velocity;
	
				if(prevNode == null) {
					node.setNextNode(mountNode);
					mountNode.setPreviousNode(node);
				} else {
					prevNode.setPreviousNode(node);
					node.setNextNode(prevNode);
				}
				
				world.spawnEntity(node);
				
				prevNode = node;
			}
			
			mountNode.setNextNode(player);

			world.spawnEntity(mountNode);
			
			/*EntityGrapplingHookNode node = new EntityGrapplingHookNode(world);
			node.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, 0, 0);

			EntityGrapplingHookNode mountNode = new EntityGrapplingHookNode(world);
			mountNode.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, 0, 0);
			
			player.startRiding(mountNode);
			
			float velocity = 1.5F;

			node.motionX = dir.x * velocity;
			node.motionY = (dir.y + 0.5) * velocity;
			node.motionZ = dir.z * velocity;

			node.setNextNode(mountNode);
			mountNode.setPreviousNode(node);
			
			mountNode.setNextNode(player);

			world.spawnEntity(mountNode);
			world.spawnEntity(node);*/
		}

		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
