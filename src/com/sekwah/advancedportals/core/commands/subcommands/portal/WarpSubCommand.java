package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.DataTag;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.commands.subcommands.CreateSubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WarpSubCommand extends CreateSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.createdesti.console"));
                return;
            }

            if (args.length == 2){
                AdvancedPortal portal = AdvancedPortalsCore.getPortalManager().getPortal("name:"+args[1]);
                if (portal == null){
                    sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.remove.noname"));
                    return;
                }
                if (!player.hasPermission("advancedportals.warp."+args[1])){
                    sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.warp.noperm"));
                    return;
                }
                sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateInsertVariables("command.warp.teleporting", args[1]));
                portal.activate(player);
            }
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.error.noname"));
        }
    }

    protected String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if(splitLoc != -1) {
            return arg.substring(0,splitLoc);
        }
        return null;
    }

    private String getTagValue(String tag){
        String[] value = tag.split(":");
        return value.length == 2 ? value[1] : null;
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.warp");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        List<String> portals = new ArrayList<>();
        for(Map.Entry<String, AdvancedPortal> portal:AdvancedPortalsCore.getPortalManager().getPortals()){
            if (sender.hasPermission("advancedportals.warp."+getTagValue(portal.getKey()))){
                portals.add(getTagValue(portal.getKey()));
            }
        }
        return portals;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.warp.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.warp.detailedhelp");
    }
}
