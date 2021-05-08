package Viewer;

import GUI.GUI;
import Model.elements.Element;
import Viewer.Image.Image;

import java.util.HashMap;
import java.util.Map;

public abstract class ElementViewer {
    private Image image;
    private Map<Element, FrameHandler> elementFrameSpeedMap;

    public ElementViewer() {
        elementFrameSpeedMap = new HashMap<>();
    }

    protected void setImage(Image image) {
        this.image = image;
    }

    protected void setElementFrameSpeedMap(Map<Element, FrameHandler> elementFrameSpeedMap) {
        this.elementFrameSpeedMap = elementFrameSpeedMap;
    }

    protected Image getImage() {
        return image;
    }

    protected Map<Element, FrameHandler> getElementFrameSpeedMap() {
        return elementFrameSpeedMap;
    }

    public abstract void update();
    public abstract void draw(Element element, GUI gui);
}
