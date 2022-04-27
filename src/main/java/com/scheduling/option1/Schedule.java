/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class Schedule implements ScheduleInterface {
    public int time = 0;

    protected List<Process> readyQueue = new ArrayList<>();
    protected List<Process> list_of_processes = new ArrayList<>();
    //set the number of resources to be 3
    protected int resource_num;

    protected List<List<Process>> resourceQueue;// = new ArrayList<>(resource_num);
    protected List<Process> waitQueue = new ArrayList<>();
    protected List<List<String>> g_chart;// = new ArrayList<>(resource_num + 1);//1 for cpu
    protected List<Integer> time_stamp = new ArrayList<>();

    public Schedule(int resource_num)
    {
        this.resource_num = resource_num;
        resourceQueue = new ArrayList<>(resource_num);
        g_chart = new ArrayList<>(resource_num + 1);
        for(int i= 0; i< resource_num + 1; i++)
        {
            if (i < resource_num){
                this.resourceQueue.add(new ArrayList<>());
            }
            this.g_chart.add(new ArrayList<>());
        }
    }

    public void add(Process p)
    {
    this.waitQueue.add(p);
    this.list_of_processes.add(p);
    }

    void initState()
    {
    this.time_stamp.add(this.time);
    for(int i=0; i<this.resource_num + 1; i++)
    {
        this.g_chart.get(i).add("__");
    }
    }

    public void addState(String value, int location)
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

    public void showChart()
    {
        System.out.println("===================GANT CHART====================");
        System.out.println("#Time ----  #CPU -----  #R1  -----  #R2 -----  #R3");
        for(int i=0; i< this.time_stamp.size(); i++)
        {
            System.out.printf("%3s  -----  %3s  -----  %3s  -----  %3s -----  %3s%n",
                    this.time_stamp.get(i),
                    this.g_chart.get(0).get(i),
                    this.g_chart.get(1).get(i),
                    this.g_chart.get(2).get(i),
                    this.g_chart.get(3).get(i));
        }
    }
/*
    public void showWaitTime()
    {
        int avg_time = 0;
        for(Process p : list_of_processes)
        {
            System.out.printf("Waiting time of %s is: %d\n", p.name, p.waiting_time);
            avg_time+= p.waiting_time;
        }
        System.out.printf("The average waiting time is: %.2f\n------------------------------------\n",
                (double) avg_time / this.list_of_processes.size());
    }
 */
    public String showWaitTime()
    {
        StringBuilder result = new StringBuilder("<html>");
        int avg_time = 0;
        for(Process p : list_of_processes)
        {
            result.append(String.format("Waiting time of %s is: %d<br/>", p.name, p.waiting_time));
            avg_time+= p.waiting_time;
        }
        result.append(String.format("<br/>The average waiting time is: %.2f<br/>>------------------------------------</html>",
                (double) avg_time / this.list_of_processes.size()));
        return result.toString();
    }
    /*
    public void showTurnAroundTime()
    {
        int avg_time = 0;
        for(Process p : list_of_processes)
        {
            System.out.printf("Turn around time of %s is: %d\n", p.name, p.getTurnAroundTime());
            avg_time+= p.getTurnAroundTime();
        }
        System.out.printf("The average turn around time is: %.2f\n------------------------------------\n" ,
                (double)avg_time / this.list_of_processes.size());
    }
     */

    public String showTurnAroundTime()
    {
        StringBuilder result = new StringBuilder("<html>");
        int avg_time = 0;
        for(Process p : list_of_processes)
        {
            result.append(String.format("Turn around time of %s is: %d<br/>", p.name, p.getTurnAroundTime()));
            avg_time+= p.getTurnAroundTime();
        }
        result.append(String.format("<br/>The average turn around time is: %.2f<br/>------------------------------------</html>",
                (double) avg_time / this.list_of_processes.size()));
        return result.toString();
    }

    public List<List<String>> getChart()
    {
        return this.g_chart;
    }

    public abstract void startProcess();
    abstract void scheduleCPU();
    abstract void scheduleResource();


}
