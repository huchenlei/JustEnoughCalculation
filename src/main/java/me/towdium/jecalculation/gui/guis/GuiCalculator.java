package me.towdium.jecalculation.gui.guis;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.towdium.jecalculation.gui.JecGui;
import me.towdium.jecalculation.gui.Resource;
import me.towdium.jecalculation.gui.drawables.*;
import me.towdium.jecalculation.data.label.ILabel;
import me.towdium.jecalculation.event.handlers.ControllerClient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Author: towdium
 * Date:   8/14/17.
 */
@ParametersAreNonnullByDefault
@SideOnly(Side.CLIENT)
public class GuiCalculator extends WContainer {
    WLabelGroup wRecent = new WLabelGroup(7, 31, 8, 1, WLabel.enumMode.PICKER);
    WLabel wLabel = new WLabel(31, 7, 20, 20, WLabel.enumMode.SELECTOR);

    public GuiCalculator() {
        add(new WPanel());
        add(new WTextField(61, 7, 64));
        add(new WButtonIcon(7, 7, 20, 20, Resource.BTN_LABEL_N, Resource.BTN_LABEL_F, "calculator.label")
                    .setListenerLeft(() -> JecGui.displayGui(new GuiLabel(l -> {
                        JecGui.displayParent();
                        JecGui.getCurrent().hand = l;
                    }))));
        add(new WButtonIcon(130, 7, 20, 20, Resource.BTN_NEW_N, Resource.BTN_NEW_F, "calculator.recipe")
                    .setListenerLeft(() -> JecGui.displayGui(true, true, new GuiRecipe())));
        add(new WButtonIcon(149, 7, 20, 20, Resource.BTN_SEARCH_N, Resource.BTN_SEARCH_F, "calculator.search"));
        add(new WLabelGroup(7, 87, 9, 4, WLabel.enumMode.RESULT));
        add(wRecent);
        add(wLabel);
        add(new WLine(52));
        add(new WIcon(151, 31, 18, 18, Resource.ICN_RECENT_N, Resource.ICN_RECENT_F, "calculator.history"));
        add(new WSwitcher(7, 56, 162, 5));
        refresh();
        List<ILabel> recent = ControllerClient.getRecent();
        if (recent.size() > 0)
            wLabel.setLabel(ControllerClient.getRecent().get(0));
        wLabel.setLsnrUpdate(() -> {
            ControllerClient.setRecent(wLabel.label);
            refresh();
        });
    }

    void refresh() {
        List<ILabel> recent = ControllerClient.getRecent();
        if (recent.size() > 1)
            wRecent.setLabel(recent.subList(1, recent.size()), 0);
    }
}
