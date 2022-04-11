package com.scheduling.option1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Schedule {
     public int time = 0;
     protected List<Process> readyQueue = new ArrayList<>();
    //set the number of resources to be 3
     protected List<List<Process>> resourceQueue = new ArrayList<>(3);
     protected List<Process> waitQueue = new ArrayList<>();
     protected List<List<String>> g_chart = new ArrayList<>(4);
     protected List<Integer> time_stamp = new ArrayList<>();

     public Schedule()
     {
         for(int i= 0; i< 4; i++)
         {
             if (i < 3){
                 this.resourceQueue.add(new ArrayList<>());
             }
             this.g_chart.add(new ArrayList<>());
         }
     }

    void add(Process p)
    {
        this.waitQueue.add(p);
    }

    void initState()
    {
        this.time_stamp.add(this.time);
        for(int i=0; i<4; i++)
        {
            this.g_chart.get(i).add("NIL");
        }
    }

    void addState(String value, int location)
    {
        int index = this.g_chart.get(location).size() - 1;
        this.g_chart.get(location).set(index, value);
    }

    public void sortTime(List<Process> Queue)
    {
        for(int i=0; i<Queue.size(); i++)
        {
            for(int j=i+1; j<Queue.size(); j++)
            {
                if(Queue.get(i).in_time > Queue.get(j).in_time)
                {
                    Process p = Queue.get(i);
                    Queue.set(i, Queue.get(j));
                    Queue.set(j, p);
                }
            }
        }
    }

    public void sortBurst(List<Process> Queue)
    {
        for(int i=0; i<Queue.size(); i++)
        {
            for(int j=i+1; j<Queue.size(); j++)
            {
                if(Queue.get(i).getBurst() > Queue.get(j).getBurst())
                {
                    Process p = Queue.get(i);
                    Queue.set(i, Queue.get(j));
                    Queue.set(j, p);
                }
            }
        }
    }

    boolean resourceIsOut()
    {
        for(List<Process> l : this.resourceQueue)
        {
            if(l.size() > 0)
            {
                return false;
            }
        }
        return true;
    }

    public void showReadyQueue()
    {
        System.out.print("Ready Queue: ");
        for (Process process : this.readyQueue) {
            System.out.printf("%s:%d | ", process.name, process.getBurst());
        }
        System.out.println();
    }

    public void setWaitReadyQueue()
    {
        for(Process p : this.readyQueue)
        {
            p.waitProcess();
        }
    }

    public void showChart()
    {
        System.out.println("#Time-----#CPU-----#R1-----#R2-----#R3");
        for(int i=0; i< this.time_stamp.size(); i++)
        {
            System.out.printf("%s-----%s-----%s-----%s-----%s%n", this.time_stamp.get(i),
            this.g_chart.get(0).get(i),this.g_chart.get(1).get(i),this.g_chart.get(2).get(i),this.g_chart.get(3).get(i));
        }
    }

    void addWaitToReady()
    {
        sortTime(this.waitQueue);
        while(this.waitQueue.size() > 0 && this.waitQueue.get(0).in_time == this.time)
        {
            String name = this.waitQueue.get(0).name;
            this.readyQueue.add(this.waitQueue.get(0));
            this.waitQueue.remove(0);
            System.out.println(name + " is added to Ready Queue!");
        }
    }
    public abstract void startProcess();
    abstract void scheduleCPU();
    abstract void scheduleResource();
}
