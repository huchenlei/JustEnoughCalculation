package me.towdium.jecalculation.gui.guis.pickers;

import mcp.MethodsReturnNonnullByDefault;
import me.towdium.jecalculation.data.label.ILabel;
import me.towdium.jecalculation.data.label.labels.LFluidStack;
import me.towdium.jecalculation.data.label.labels.LFluidTag;
import me.towdium.jecalculation.data.label.labels.LItemTag;
import me.towdium.jecalculation.gui.guis.IGui;
import me.towdium.jecalculation.gui.widgets.WIcon;
import me.towdium.jecalculation.gui.widgets.WLabelScroll;
import me.towdium.jecalculation.gui.widgets.WSearch;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.towdium.jecalculation.gui.Resource.ICN_TEXT;

/**
 * Author: towdium
 * Date:   17-9-28.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class PickerSimple extends IPicker.Impl implements IGui {
    /**
     * @param labels label to be displayed for selection
     */
    public PickerSimple(List<ILabel> labels) {
        WLabelScroll ls = new WLabelScroll(7, 33, 8, 7, false).setLabels(labels)
                .setLsnrClick((i, v) -> notifyLsnr(i.get(v).getLabel()));
        add(new WSearch(26, 7, 90, ls));
        add(new WIcon(7, 7, 20, 20, ICN_TEXT, "common.search"));
        add(ls);
    }

    public static class FluidStack extends PickerSimple {
        public FluidStack() {
            super(ForgeRegistries.FLUIDS.getValues().stream().filter(i -> i.isSource(i.getDefaultState()))
                    .map(i -> new LFluidStack(1000, i)).collect(Collectors.toList()));
        }
    }

    public static class Tag extends PickerSimple {
        public Tag() {
            super(generate());
        }

        static List<ILabel> generate() {
            Stream<LItemTag> items = ItemTags.getCollection().getIDTagMap().entrySet().stream()
                    .filter(i -> !i.getValue().getAllElements().isEmpty())
                    .map(i -> new LItemTag(i.getKey()))
                    .sorted(Comparator.comparing(LItemTag::getName));
            Stream<LFluidTag> fluids = FluidTags.getCollection().getIDTagMap().entrySet().stream()
                    .filter(i -> !i.getValue().getAllElements().isEmpty())
                    .map(i -> new LFluidTag(i.getKey()))
                    .sorted(Comparator.comparing(LFluidTag::getName));
            return Stream.of(items, fluids).flatMap(i -> i).collect(Collectors.toList());
        }
    }
}
