package tauri.dev.jsg.integration;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import tauri.dev.jsg.capability.endpoint.ItemEndpointCapability;
import tauri.dev.jsg.capability.endpoint.ItemEndpointInterface;
import tauri.dev.jsg.config.JSGConfig;
import tauri.dev.jsg.item.oc.ItemWirelessEndpoint;

public class OCWrapperLoaded implements OCWrapperInterface {

    public void sendSignalToReachable(Node node, Context invoker, String name, Object... params) {
        if (node == null) return;
        for (Node targetNode : node.reachableNodes()) {

            if (targetNode.host() instanceof Machine) {
                Machine machine = (Machine) targetNode.host();

                // If the receiving machine was a sender
                boolean caller = machine == invoker;

                Object[] array = new Object[params.length + 2];
                array[0] = node.address();
                array[1] = caller;

                for (int i = 0; i < params.length; i++)
                    array[i + 2] = params[i];

                machine.signal(name, array);
            }
        }
    }

    @Override
    public Node createNode(TileEntity environment, String componentName) {
        return Network.newNode((Environment) environment, Visibility.Network).withComponent(componentName, Visibility.Network).create();
    }

    @Override
    public void joinOrCreateNetwork(TileEntity tileEntity) {
        Network.joinOrCreateNetwork(tileEntity);
    }

    @Override
    public boolean isModLoaded() {
        return true;
    }

    @Override
    public void sendWirelessPacketPlayer(String packetPrefix, EntityPlayer player, ItemStack stack, String address, short port, Object[] data) {
        ItemEndpointInterface endpointStack = stack.getCapability(ItemEndpointCapability.ENDPOINT_CAPABILITY, null);

        if (endpointStack.hasEndpoint()) {
            endpointStack.resetEndpointCounter(player.getEntityWorld().getTotalWorldTime());
        } else {
            ItemWirelessEndpoint endpoint = new ItemWirelessEndpoint(player);
            Network.joinWirelessNetwork(endpoint);

            endpointStack.setEndpoint(endpoint, player.getEntityWorld().getTotalWorldTime());
        }

        // Broadcast
        if (address.isEmpty())
            address = null;

        Packet packet = Network.newPacket(packetPrefix + "-" + player.getName(), address, port, data);

        Network.sendWirelessPacket((WirelessEndpoint) endpointStack.getEndpoint(), JSGConfig.integrationsConfig.ocIntegrationWirelessRange, packet);
    }

    @Override
    public void joinWirelessNetwork(Object endpoint) {
        Network.joinWirelessNetwork((WirelessEndpoint) endpoint);
    }

    @Override
    public void leaveWirelessNetwork(Object endpoint) {
        Network.leaveWirelessNetwork((WirelessEndpoint) endpoint);
    }

    @Override
    public void updateWirelessNetwork(Object endpoint) {
        Network.updateWirelessNetwork((WirelessEndpoint) endpoint);
    }
}
