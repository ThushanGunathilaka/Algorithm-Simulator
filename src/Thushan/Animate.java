/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Thushan;

import GUI.Cell;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Design
 */
public class Animate {
    private int delay = 2000;
    private final Cell panel;
    private final Rectangle from;
    private final Rectangle to;
    private long startTime;

    public Animate(Cell panel, Rectangle from, Rectangle to,int delay) {
        this.panel = panel;
        this.from = from;
        this.to = to;
        this.delay = delay;
    }
    
    public void setDelay(int delay){
        this.delay = delay;
    }

    public void start() {
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long duration = System.currentTimeMillis() - startTime;
                double progress = (double)duration / (double)delay;
                if (progress > 1f) {
                    progress = 1f;
                    ((Timer)e.getSource()).stop();
                }
                Rectangle target = calculateProgress(from, to, progress);
                panel.setBounds(target);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        startTime = System.currentTimeMillis();
        timer.start();
    }
        
    public Rectangle calculateProgress(Rectangle startBounds, Rectangle targetBounds, double progress) {
        Rectangle bounds = new Rectangle();
        if (startBounds != null && targetBounds != null) {
            bounds.setLocation(calculateProgress(startBounds.getLocation(), targetBounds.getLocation(), progress));
            bounds.setSize(calculateProgress(startBounds.getSize(), targetBounds.getSize(), progress));
        }
        return bounds;
    }
        
    public Point calculateProgress(Point startPoint, Point targetPoint, double progress) {
        Point point = new Point();
        if (startPoint != null && targetPoint != null) {
            point.x = calculateProgress(startPoint.x, targetPoint.x, progress);
            point.y = calculateProgress(startPoint.y, targetPoint.y, progress);
        }
        return point;
    }

    public int calculateProgress(int startValue, int endValue, double fraction) {
        int value;
        int distance = endValue - startValue;
        value = (int)Math.round((double)distance * fraction);
        value += startValue;
        return value;
    }

    public Dimension calculateProgress(Dimension startSize, Dimension targetSize, double progress) {
        Dimension size = new Dimension();
        if (startSize != null && targetSize != null) {
            size.width = calculateProgress(startSize.width, targetSize.width, progress);
            size.height = calculateProgress(startSize.height, targetSize.height, progress);
        }
        return size;
    }
}