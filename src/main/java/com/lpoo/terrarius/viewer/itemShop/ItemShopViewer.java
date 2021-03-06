package com.lpoo.terrarius.viewer.itemShop;

import com.lpoo.terrarius.gui.GUI;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.model.game.elements.hero.Hero;
import com.lpoo.terrarius.model.game.items.Item;
import com.lpoo.terrarius.model.itemShop.ItemListing;
import com.lpoo.terrarius.model.itemShop.ItemShop;
import com.lpoo.terrarius.viewer.image.ColoredImage;
import com.lpoo.terrarius.viewer.image.StillImage;
import com.lpoo.terrarius.viewer.Viewer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.lpoo.terrarius.viewer.itemShop.ItemShopViewerConstants.*;

public class ItemShopViewer extends Viewer<ItemShop> {

    private StillImage titleImage;
    private boolean listingNeedsUpdate;

    private final Map<ItemListing, Position> itemListingPositionMap = new HashMap<>();
    private final Map<String, ColoredImage> itemListingIconMap = new HashMap<>();

    public ItemShopViewer(){
        super();
        titleImage = new StillImage();
        try {
            titleImage.load("Images/ItemShop/Title.txt");
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            titleImage = null;
        }

        this.listingNeedsUpdate = true;

    }

    private void updateListings(ItemShop model) throws FileNotFoundException, URISyntaxException {

        int i = 0;

        itemListingIconMap.clear();
        itemListingPositionMap.clear();

        for (ItemListing itemListing : model.getOptions()){

            if(i > MAX_LISTINGS_PER_PAGE) break;

            Position position = new Position( (i % MAX_LISTINGS_PER_LINE) + 1, (i / MAX_LISTINGS_PER_LINE)+1);

            itemListingPositionMap.put(itemListing, position);

            ColoredImage iconImage = new ColoredImage();
            iconImage.load("Images/Items/" + itemListing.getItem() + "Icon.txt");

            itemListingIconMap.put(itemListing.getItem(), iconImage);

            i++;
        }

        this.listingNeedsUpdate = false;

    }

    private void drawItemListing(GUI gui, ItemListing itemListing, Boolean selected){

        ColoredImage iconImage = itemListingIconMap.get(itemListing.getItem());

        Position screen_position = new Position(
                LISTINGS_X + LISTINGS_WIDTH/(MAX_LISTINGS_PER_LINE + 1) * itemListingPositionMap.get(itemListing).getX(),
                LISTINGS_Y + LISTINGS_HEIGHT/(MAX_LISTINGS_PER_ROW + 1) * itemListingPositionMap.get(itemListing).getY()
                        - (iconImage.getDimension().getHeight()+4)/2
        );

        gui.drawString(
                screen_position.getX() - itemListing.getItem().length()/2, screen_position.getY(),
                itemListing.getItem(),
                "#FFFFFF",
                "#000000");

        if (selected){
            gui.drawCharacter(
                    screen_position.getX() - itemListing.getItem().length()/2 - 2,
                    screen_position.getY(),
                    '>');
        }

        iconImage.draw(new Position(
                screen_position.getX() - iconImage.getDimension().getWidth()/2,
                screen_position.getY() + 2),
                Element.Orientation.RIGHT, gui);

        String priceString =  itemListing.getPrice() + "p";

        gui.drawString(
                screen_position.getX() - priceString.length()/2,
                screen_position.getY() + iconImage.getDimension().getHeight() + 3,
                priceString,
                "#FFFFFF",
                "#000000"
        );

    }

    private void drawSideBar(GUI gui, ItemShop model){

        Hero hero = model.getHero();

        gui.drawString(
                SIDEBAR_X + 1,
                SIDEBAR_Y + SIDEBAR_OFFSET + 1,
                "Available points: ",
                "#FFFFFF",
                "#000000"
        );

        gui.drawString(
                SIDEBAR_X + 1,
                SIDEBAR_Y + SIDEBAR_OFFSET + 3,
                model.getHero().getStats().getCurrentPoints() + "p",
                "#FFFFFF",
                "#000000"
        );

        gui.drawCharacter(SIDEBAR_X + 1,
                SIDEBAR_Y + SIDEBAR_OFFSET + 3 + (SIDEBAR_HEIGHT - SIDEBAR_OFFSET - 3)/(10) * model.getSelectedSlot(),
                '>');

        for (int i = 1; i <= 9; i++){

            gui.drawString(
                    SIDEBAR_X + 3,
                    SIDEBAR_Y + SIDEBAR_OFFSET + 3 + (SIDEBAR_HEIGHT - SIDEBAR_OFFSET - 3)/(10) * i,
                    "" + i,
                    "#FFFFFF",
                    "#000000"
            );

            Item item = hero.getToolBar().getItem(i);

            if (item == null) continue;

            ColoredImage iconImage = itemListingIconMap.get(item.getComponentName());

            if(iconImage == null) continue;

            Position position = new Position(
                    SIDEBAR_X + (SIDEBAR_WIDTH)/2,
                    SIDEBAR_Y + SIDEBAR_OFFSET + 3 + (SIDEBAR_HEIGHT - SIDEBAR_OFFSET - 3)/(10) * i - iconImage.getDimension().getHeight()/2
            );

            iconImage.draw(position, Element.Orientation.RIGHT, gui);

        }

        for(int i = 0; i < SIDEBAR_HEIGHT; i++){
            gui.drawCharacter(SIDEBAR_WIDTH, i, '|');
        }

    }

    @Override
    public void draw(GUI gui, ItemShop model) throws IOException, URISyntaxException {

        if (listingNeedsUpdate) updateListings(model);

        Position titlePosition = new Position(
                TITLE_X + TITLE_WIDTH/2 - titleImage.getDimensions().getWidth()/2,
                TITLE_Y + TITLE_HEIGHT/2 - titleImage.getDimensions().getHeight()/2);

        gui.clear();

        titleImage.draw(titlePosition, Element.Orientation.RIGHT, gui);

        for ( ItemListing itemListing: itemListingPositionMap.keySet()) {

            boolean selected = model.getOption(model.getSelectedIndex()).equals(itemListing);

            drawItemListing(gui, itemListing, selected);
        }

        drawSideBar(gui, model);

        gui.refresh();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
