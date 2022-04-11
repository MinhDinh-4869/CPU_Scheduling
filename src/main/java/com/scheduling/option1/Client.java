package com.scheduling.option1;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args)
    {
        ScheduleInterface s = new PreEmptiveSJFSchedule();

        Process p1 = new Process(s, "P1");
        Process p2 = new Process(s, "P2");
        Process p3 = new Process(s, "P3");

        p1.in_time = 3;
        p2.in_time = 4;
        p3.in_time = 0;

        List<Integer> p1_burst = new ArrayList<>();
        List<Integer> p2_burst = new ArrayList<>();
        List<Integer> p3_burst = new ArrayList<>();

        List<Integer> p1_resource = new ArrayList<>();
        List<Integer> p2_resource = new ArrayList<>();
        List<Integer> p3_resource = new ArrayList<>();

        p1_burst.add(3);
        p1_burst.add(5);
        p2_burst.add(2);
        p3_burst.add(7);


        p1_resource.add(3);
        p2_resource.add(5);
        p3_resource.add(2);

        p1.setCpuBurst(p1_burst);
        p2.setCpuBurst(p2_burst);
        p3.setCpuBurst(p3_burst);

        p1.setResourceBurst(p1_resource, 1);
        p2.setResourceBurst(p2_resource, 1);
        p3.setResourceBurst(p3_resource, 0);

        s.startProcess();

        System.out.println("========================RESULT========================");
        s.showWaitTime();
        s.showTurnAroundTime();

        s.showChart();
    }
}
