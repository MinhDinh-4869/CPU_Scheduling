/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.util.List;

public class Process {
    String name;
    //public int resource_id = 0;
    boolean canJump = false;
    int waiting_time = 0;
    public int in_time;
    public int in_system_time;
    public int get_out_time;

    private ScheduleInterface schedule;
    private List<Integer> cpu_burst;
    private List<Integer> resource_burst;
    public List<Integer> resource_id;

    public Process(String name)
    {
        this.name = name;
    }

    public void setSchedule(ScheduleInterface schedule)
    {
        this.schedule = schedule;
        schedule.add(this);
    }

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

    public void setResourceBurst(List<Integer> resource_burst, List<Integer> resource_id)
    {
        //set the resource properties for Process and add it to the schedule
        this.resource_burst = resource_burst;
        this.resource_id = resource_id;
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

    public void runResource()
    {
        System.out.println(this.name + " is using resource no. " + this.resource_id);
        this.schedule.addState(this.name, 1 + this.resource_id.get(0));
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
        this.resource_id.remove(0);//remove the corresponding resource id
        this.canJump = (this.cpu_burst.size() > 0);
    }

    public void setOutTime(int get_out_time)
    {
        this.get_out_time = get_out_time;
    }

    public int getTurnAroundTime()
    {
        return this.get_out_time - this.in_system_time + 1;
    }
}
