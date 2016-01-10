package thebetweenlands.event.entity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingEvent;
import thebetweenlands.entities.mobs.EntityDarkDruid;
import thebetweenlands.entities.mobs.IEntityBL;
import thebetweenlands.manual.ManualManager;

/**
 * Created on 07/12/2015.
 */
public class PageDiscoveringEvent {
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if (event.entityLiving != null && event.entityLiving instanceof EntityLiving){
            if (event.entityLiving instanceof IEntityBL )
                ManualManager.playerDiscoverPage((EntityLiving) event.entityLiving, ((IEntityBL) event.entityLiving).pageName(), ManualManager.EnumManual.GUIDEBOOK);
            if (event.entityLiving instanceof EntityDarkDruid)
                ManualManager.playerDiscoverPage((EntityLiving) event.entityLiving, "darkDruid", ManualManager.EnumManual.GUIDEBOOK);
        }
    }
}