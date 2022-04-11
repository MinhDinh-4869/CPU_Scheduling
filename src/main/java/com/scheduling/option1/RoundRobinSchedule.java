package com.scheduling.option1;

import java.util.List;

public class RoundRobinSchedule extends Schedule implements ScheduleInterface{
    private Process running = null;
    private int running_counter =0;
    private final int q;

    public RoundRobinSchedule(int q)
    {
        super();
        this.q = q;
    }

    public void startProcess()
    {
        while(true) {
            System.out.println("#Time" + this.time);
            addWaitToReady();
            showReadyQueue();

            initState();

            scheduleCPU();
            scheduleResource();

            if(this.running ==null && this.readyQueue.size() ==0 && resourceIsOut())
            {
                break;
            }

            this.time++;
        }
    }

    public void scheduleCPU()
    {
        //Check for running
        if(this.running == null)
        {
            //check for remaining ready queue
            if(this.readyQueue.size() > 0)
            {
                this.running = this.readyQueue.get(0);
                this.readyQueue.remove(0);

                this.running.run();
                this.running_counter++;

                if(this.running_counter == q)
                {
                    this.running_counter = 0;
                    this.readyQueue.add(this.running);
                    this.running = null;
                }
            }
        }
        else//assume that the running is not null
        {
            //check for running counter
            if(this.running_counter == q || this.running.getBurst() == 0)
            {
                if(this.running.getBurst() == 0)
                {
                    this.running.removeDoneBurst();
                    if(this.running.canJump)
                    {
                        this.resourceQueue.get(this.running.resource_id).add(this.running);
                    }
                    else
                    {
                        System.out.println(this.running.name + " Exit System!");
                        this.running.setOutTime(this.time);
                    }

                    this.running = null;
                    this.running_counter = 0;

                    if(this.readyQueue.size() > 0)
                    {
                        this.running = this.readyQueue.get(0);
                        this.readyQueue.remove(0);

                        this.running.run();
                        this.running_counter++;
                    }
                }
                else//running counter reaches its limit
                {
                    this.readyQueue.add(this.running);

                    this.running = this.readyQueue.get(0);
                    this.readyQueue.remove(0);

                    this.running_counter = 0;
                    this.running.run();
                    this.running_counter++;
                }
            }
            else
            {
                this.running.run();
                this.running_counter++;
            }
        }

        setWaitReadyQueue();
    }

    void scheduleResource()
    {
        for(List<Process> l : this.resourceQueue)
        {
            if(l.size() > 0) //there is process in resourceQueue
            {
                l.get(0).runResource();
                //after run, check the resource for being 0
                //if yes, move it to ready queue for the next second
                //The cpu scheduling phase does not need this because we set the resource scheduling to be the end
                //phase, it needs to prepare for the next loop
                if(l.get(0).getResourceBurst() == 0) //make sure no 0 burst in the resource
                {
                    l.get(0).removeDoneResourceBurst();
                    //moveToReady(l);
                    if(l.get(0).canJump)
                    {
                        this.readyQueue.add(l.get(0));
                        l.remove(0);
                    }
                    else
                    {
                        System.out.println(l.get(0).name + " Exit System");
                        l.get(0).setOutTime(this.time);
                        l.remove(0);
                    }
                }
            }
        }
    }
}
