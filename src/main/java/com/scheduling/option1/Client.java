package com.scheduling.option1;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args)
    {
        ScheduleInterface s = new PreEmptiveSJFSchedule();
        //ScheduleInterface s = new RoundRobinSchedule(3);

        Process p1 = new Process(s, "P1");
        Process p2 = new Process(s, "P2");
        Process p3 = new Process(s, "P3");
        Process p4 = new Process(s, "P4");

        p1.in_time = 0;
        p2.in_time = 2;
        p3.in_time = 10;
        p4.in_time = 11;

        List<Integer> p1_burst = new ArrayList<>();
        List<Integer> p2_burst = new ArrayList<>();
        List<Integer> p3_burst = new ArrayList<>();
        List<Integer> p4_burst = new ArrayList<>();

        List<Integer> p1_resource = new ArrayList<>();
        List<Integer> p2_resource = new ArrayList<>();
        List<Integer> p3_resource = new ArrayList<>();
        List<Integer> p4_resource = new ArrayList<>();

        List<Integer> p1_resource_id = new ArrayList<>();
        List<Integer> p2_resource_id = new ArrayList<>();
        List<Integer> p3_resource_id = new ArrayList<>();
        List<Integer> p4_resource_id = new ArrayList<>();

        p1_burst.add(8);
        p2_burst.add(1);
        p3_burst.add(6);
        p4_burst.add(3);

        p1_burst.add(1);
        p2_burst.add(2);
        p3_burst.add(2);



        p1_resource.add(5);
        p2_resource.add(8);
        p3_resource.add(5);
        p4_resource.add(20);

        p2_resource.add(5);
        p3_resource.add(3);

        p1_resource_id.add(0);
        p2_resource_id.add(1);
        p3_resource_id.add(0);
        p4_resource_id.add(1);
        p2_resource_id.add(0);
        p3_resource_id.add(1);


        p1.setCpuBurst(p1_burst);
        p2.setCpuBurst(p2_burst);
        p3.setCpuBurst(p3_burst);
        p4.setCpuBurst(p4_burst);

        p1.setResourceBurst(p1_resource, p1_resource_id);
        p2.setResourceBurst(p2_resource, p2_resource_id);
        p3.setResourceBurst(p3_resource, p3_resource_id);
        p4.setResourceBurst(p4_resource, p4_resource_id);

        s.startProcess();

        System.out.println("=========================RESULT=========================");
        s.showWaitTime();
        s.showTurnAroundTime();

        s.showChart();
    }
}
