package com.scheduling.option1;

import java.util.List;

public class Process {
    public String name;
    public int resource_id = 0;
    public boolean canJump = false;

    int waiting_time = 0;
    int get_out_time;

    private final ScheduleInterface schedule;
    public int in_time;
    private List<Integer> cpu_burst;
    private List<Integer> resource_burst;

    public Process(ScheduleInterface schedule, String name)
    {
        this.name = name;
        this.schedule = schedule;
        schedule.add(this);
    }

    public void setCpuBurst(List<Integer> cpu_burst)
    {
        this.cpu_burst = cpu_burst;
    }

    public void setResourceBurst(List<Integer> resource_burst, int resource_id)
    {
        //set the resource properties for Process and add it to the schedule
        this.resource_burst = resource_burst;
        this.resource_id = resource_id;
    }

    public void setInTime(int in_time)
    {
        this.in_time = in_time;
    }

    public int getBurst()
    {
        return this.cpu_burst.get(0);
    }

    public int getResourceBurst()
    {
        return this.resource_burst.get(0);
    }

    public void run()
    {
        System.out.println(this.name + " is running...");
        this.schedule.addState(this.name, 0);
        this.cpu_burst.set(0, this.cpu_burst.get(0) - 1);
    }

    public void waitProcess()
    {
        this.waiting_time++;
    }

    public void rollBackWaitTime()
    {
        this.waiting_time--;
    }

    public void showWaitTime()
    {
        System.out.println(this.name + " 's wait time : " + this.waiting_time);
    }

    public void runResource()
    {
        System.out.println(this.name + " is using resource no. " + this.resource_id);
        this.schedule.addState(this.name, 1 + this.resource_id);
        this.resource_burst.set(0, this.resource_burst.get(0) - 1);
    }

    public void removeDoneBurst()
    {
        this.cpu_burst.remove(0);
        this.canJump = this.resource_burst != null && (this.resource_burst.size() > 0);
    }

    public void removeDoneResourceBurst()
    {
        this.resource_burst.remove(0);
        this.canJump = (this.cpu_burst.size() > 0);
    }

    public void setOutTime(int get_out_time)
    {
        this.get_out_time = get_out_time;
    }

    public int getOutTime()
    {
        return this.get_out_time;
    }

    public int getTurnAroundTime()
    {
        return this.get_out_time - this.in_time;
    }
}
