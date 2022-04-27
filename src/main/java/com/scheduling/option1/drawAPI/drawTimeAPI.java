package com.scheduling.option1.drawAPI;

import com.scheduling.option1.ScheduleInterface;

import javax.swing.*;
import java.awt.*;

public class drawTimeAPI {
    public drawTimeAPI(ScheduleInterface schedule){
        JDialog turnAroundTime = new JDialog();
        JDialog waitTime = new JDialog();

        turnAroundTime.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        waitTime.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel turnAround = new JLabel(schedule.showTurnAroundTime());
        JLabel wait = new JLabel(schedule.showWaitTime());

        turnAroundTime.add(turnAround, BorderLayout.CENTER);
        waitTime.add(wait, BorderLayout.CENTER);

        turnAroundTime.pack();
        waitTime.pack();

        turnAroundTime.setVisible(true);
        waitTime.setVisible(true);
    }
}
