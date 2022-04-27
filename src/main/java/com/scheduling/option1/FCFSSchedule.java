/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.util.List;

public class FCFSSchedule extends Schedule implements ScheduleInterface{
    public FCFSSchedule(int resource_num)
    {
        super(resource_num);
    }

    public void startProcess()
    {
        while(true) {
            addWaitToReady();

            showReadyQueue();

            initState();

            scheduleCPU();
            scheduleResource();

            if (this.readyQueue.size() == 0 && resourceIsOut()) {
                break;
            }
            this.time ++;
        }
    }

    void scheduleCPU()
    {
        if(this.readyQueue.size() > 0)
        {
            //check for the first process in the readyQueue
            Process running = this.readyQueue.get(0);
            if(running.getBurst() == 0)
            {
                running.removeDoneBurst();
                if(running.canJump)//has resource
                {
                    //this.resourceQueue.get(running.resource_id).add(running);
                    this.resourceQueue.get(running.resource_id.get(0)).add(running);//for multiple resource assignment
                    this.readyQueue.remove(0);
                }
                else{
                    //no more resource
                    System.out.println(running.name + " Exit System!");
                    this.readyQueue.get(0).setOutTime(this.time - 1);
                    this.readyQueue.remove(0);
                }

                if(this.readyQueue.size() > 0)
                {
                    running = this.readyQueue.get(0);
                    running.run();
                    running.rollBackWaitTime();
                }
            }
            else
            {
                running.run();
                running.rollBackWaitTime();
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
