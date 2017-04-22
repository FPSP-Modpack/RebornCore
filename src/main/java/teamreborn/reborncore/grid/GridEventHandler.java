package teamreborn.reborncore.grid;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import teamreborn.reborncore.api.power.GridJoinEvent;
import teamreborn.reborncore.api.power.IGridConnection;
import teamreborn.reborncore.api.registry.RebornRegistry;
import teamreborn.reborncore.api.registry.impl.EventRegistry;

import java.util.HashMap;

/**
 * Created by Mark on 22/04/2017.
 */
@RebornRegistry
@EventRegistry
public class GridEventHandler {

	public static HashMap<Integer, GridWorldManager> worldManagerHashMap = new HashMap<>();


	@SubscribeEvent
	public void worldTick(TickEvent.WorldTickEvent event){
		if(event.phase == TickEvent.Phase.START){
			getWorldManagerFromID(event.world.provider.getDimension()).tick(event);
		}
	}

	@SubscribeEvent
	public void gridJoin(GridJoinEvent event){
		if(event.getWorld().isRemote){
			return;
		}
		joinOrCreatePowerGrid(event.getWorld(), event.getPos(), event.getConnection());
	}


	public static GridWorldManager getWorldManagerFromID(int worldID){
		if(worldManagerHashMap.containsKey(worldID)){
			return worldManagerHashMap.get(worldID);
		} else {
			GridWorldManager gridWorldManager = new GridWorldManager();
			worldManagerHashMap.put(worldID, gridWorldManager);
			return gridWorldManager;
		}
	}

	public PowerGrid joinOrCreatePowerGrid(World world, BlockPos pos, IGridConnection gridConnection){
		GridWorldManager gridWorldManager = getWorldManagerFromID(world.provider.getDimension());
		return  gridWorldManager.joinOrCreatePowerGrid(world, pos, gridConnection);
	}


}