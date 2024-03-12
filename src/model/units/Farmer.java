package model.units;

import java.awt.Point;
import javax.swing.Timer;

public class Farmer extends MovingUnits{
    private boolean isSelected = false; // 用于标记农夫是否被选中
    private Timer moveTimer; //used to control the movement of the farmer

    public Farmer() {
        super(200, 200); //initialize the position of the farmer

        // 初始化计时器，控制农夫的移动
        moveTimer = new Timer(10, e -> move());
        moveTimer.start();
    }

    //the method to move the unit to the destination
    @Override
     public void move() {
        //set a speed for the farmers movement
        int speed = 5;

        // 计算目的地和当前位置之间的差值
        int dx = destination.x - position.x;
        int dy = destination.y - position.y;

        // 计算到目的地的直线距离
        double distance = Math.sqrt(dx * dx + dy * dy);

        // 如果距离小于或等于速度，直接设置位置为目的地，避免超过目的地
        if (distance <= speed) {
            System.out.println("Farmer reached destination");
            position.setLocation(destination);
            return;
        }

        // 计算单位向量（即方向向量，长度为1）
        double unitX = dx / distance;
        double unitY = dy / distance;

        // 计算本次移动的实际向量（方向向量 * 速度）
        int moveX = (int) (unitX * speed);
        int moveY = (int) (unitY * speed);

        // 更新位置
        position.translate(moveX, moveY);
        System.out.println("Farmer moving to " + position);
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    //check if the click is within the circle
    public boolean isClickWithinCircle(Point clickPoint, int radius) {
        return clickPoint.distance(position) <= radius;
    }
    public void startMoveTimer() {
        if (moveTimer != null && !moveTimer.isRunning()) {
            moveTimer.start();
        }
    }

    public void stopMoveTimer() {
        if (moveTimer != null && moveTimer.isRunning()) {
            moveTimer.stop();
        }
    }
}

