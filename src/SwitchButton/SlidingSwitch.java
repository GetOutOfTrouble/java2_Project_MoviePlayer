package SwitchButton;

import javafx.animation.PathTransition;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.util.Duration;
public class SlidingSwitch extends HBox {

    /**
     * Represents the amount of time the thumb takes to move from one side
     * to the other in milliseconds.
     * 250ms is fast, 500ms (default) is about medium speed, 750ms is slow
     */
    private long speed;

    /**
     * Represents the state of the switch.
     * {@code true} if the switch is on {@code false} if the switch is off
     */
    private boolean isOn;

    /** Represents the circular button toggling on and off */
    private Circle thumb;

    /** Represents the color of the thumb when on */
    private String onColor;

    /** Represents the color of the thumb when off */
    private String offColor;

    /**
     * Constructs a SlidingSwitch instantiating the thumb and
     * setting default field values.
     */
    public SlidingSwitch() {
        this.speed = 500;
        this.isOn = false;
        this.thumb = new Circle(15, 15, 15);
        this.offColor = "#8e8e8e";
        this.onColor = "#1e73df";
        build();
    }

    /**
     * Constructs a SlidingSwitch setting a specified speed,
     * instantiating the thumb and setting default field values.
     *
     * @param speed the time it takes to toggle on/off in ms.
     */
    public SlidingSwitch(long speed){
        this.speed = speed;
        this.isOn = false;
        this.thumb = new Circle(15, 15, 15);
        this.offColor = "#707070";
        this.onColor = "#1e73df";
        build();
    }

    /**
     * Sets default style properties for the container and the thumb.
     * Changes the status when clicked.
     * Called by the constructor.
     */
    private void build(){
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.getStyleClass().add("animated-switch");
        this.setPrefWidth(60);
        this.setPrefHeight(30);

        thumb.getStyleClass().add("animated-thumb");
        this.getChildren().add(thumb);

    }

    /**
     * Toggles the status based on the current status.
     * @see #turnOff()
     * @see #turnOn()
     */
    public void toggleStatus(){
        if(isOn){
            turnOff();
        }else {
            turnOn();
        }
    }

    /**
     * Creates and plays an animation that moves the thumb to the left
     * and toggles the status and style to off.
     */
    private void turnOff(){
        Path offPath = new Path();
        offPath.getElements().add(new MoveTo(this.getPrefWidth() - thumb.getCenterX(), thumb.getCenterY()));
        offPath.getElements().add(new LineTo(thumb.getCenterX(), thumb.getCenterY()));

        PathTransition offMove = new PathTransition();
        offMove.setDuration(Duration.millis(speed));
        offMove.setPath(offPath);
        offMove.setNode(thumb);
        offMove.play();

        isOn = false;
        thumb.setStyle("-base-gradient:" + offColor);
    }

    /**
     * Creates and plays an animation that moves the thumb to the right
     * and toggles the status and style to on.
     */
    private void turnOn(){
        Path onPath = new Path();
        onPath.getElements().add(new MoveTo(thumb.getCenterX(), thumb.getCenterY()));
        onPath.getElements().add(new LineTo(this.getPrefWidth() - thumb.getCenterX(), thumb.getCenterY()));

        PathTransition onMove = new PathTransition();
        onMove.setDuration(Duration.millis(speed));
        onMove.setPath(onPath);
        onMove.setNode(thumb);
        onMove.play();

        isOn = true;
        thumb.setStyle("-base-gradient:" + onColor);
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
        toggleStatus();
    }

    public Circle getThumb() {
        return thumb;
    }

    public void setThumb(Circle thumb) {
        this.thumb = thumb;
    }

    public String getOnColor() {
        return onColor;
    }

    public void setOnColor(String onColor) {
        this.onColor = onColor;
        thumb.setStyle("-fx-bg: " + onColor);
    }

    public String getOffColor() {
        return offColor;
    }

    public void setOffColor(String offColor) {
        this.offColor = offColor;
        thumb.setStyle("-fx-bg: " + offColor);
    }

    @Override
    public String toString(){
        return "SlidingSwitch { Speed: " + speed + ", isOn: " + isOn + " }";
    }
}