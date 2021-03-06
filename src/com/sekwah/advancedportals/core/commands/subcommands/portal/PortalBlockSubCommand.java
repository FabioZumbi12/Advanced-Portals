package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.List;

public class PortalBlockSubCommand implements SubCommand {

    private final AdvancedPortalsCore portalsCore;

    public PortalBlockSubCommand(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.playeronly"));
        }
        else {
            player.giveWool("PURPLE", "\u00A75Portal Block Placer"
                    , "\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block",
                            "\u00A7rLeft Click: Rotate portal block");
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.portalblock"));
        }

    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.selector.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.selector.detailedhelp");
    }
}
