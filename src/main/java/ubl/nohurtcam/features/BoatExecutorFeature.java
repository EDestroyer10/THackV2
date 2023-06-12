package ubl.nohurtcam.features;

import ubl.nohurtcam.feature.Feature;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import static ubl.nohurtcam.NoHurtCam.MC;

//Abused on the LiveOverFlow Server by Logging4J
public class BoatExecutorFeature extends Feature
{

    public BoatExecutorFeature()
    {
        super("BoatExecutor", "be ba be be bo be ba be be bo");
    }

    @Override
    public void onEnable() {
        if(!(MC.player.getVehicle() instanceof BoatEntity boat)){
            return;
        }
        Vec3d originalPos = boat.getPos();
        boat.setPosition(originalPos.add(0, 0.05, 0));
        VehicleMoveC2SPacket groundPacket = new VehicleMoveC2SPacket(boat);
        boat.setPosition(originalPos.add(0, 20, 0));
        VehicleMoveC2SPacket skyPacket = new VehicleMoveC2SPacket(boat);
        boat.setPosition(originalPos);
        for (int i = 0; i < 20; i++){
            MC.player.networkHandler.sendPacket(skyPacket);
            MC.player.networkHandler.sendPacket(groundPacket);
        }
        MC.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(boat));
    }
}