/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.util.List;

public class NonPreEmptiveSJFSchedule extends Schedule implements ScheduleInterface{
    public NonPreEmptiveSJFSchedule(int resource_num)
    {
        super(resource_num);
    }

    private Process running = null;

    public void startProcess()
    {
        while(true)
        {
            System.out.println("#Time = " + this.time);
            addWaitToReady();

            showReadyQueue();

            initState();
            scheduleCPU();
            scheduleResource();
            if(this.running == null && this.readyQueue.size() == 0 &&  resourceIsOut())
                break;
            this.time++;
            System.out.println("-------------------------");
        }
    }

    void scheduleCPU()
    {
        //check for running
        if(this.running == null)
        {
            //look for readyQueue
            if(this.readyQueue.size() > 0)
            {
                //assign the running
                sortBurst(this.readyQueue);
                this.running = this.readyQueue.get(0);
                this.readyQueue.remove(0);
                this.running.run();
            }
        }

        //assume that the current running is not null
        //check for burst value
        else {//this.running != null
            if (this.running.getBurst() == 0) {//process runs out of burst
                this.running.removeDoneBurst();
                if (this.running.canJump) {
                    //move to the appropriate resource
                    //this.resourceQueue.get(this.running.resource_id).add(this.running);
                     this.resourceQueue.get(this.running.resource_id.get(0)).add(this.running);
                } else {
                    System.out.println(this.running.name + " Exit System!");
                    this.running.setOutTime(this.time - 1);
                }

                this.running = null; //set the running to be null

                //check for remaining process in the readyQueue
                if(this.readyQueue.size() > 0)
                {
                    sortBurst(this.readyQueue);
                    this.running = this.readyQueue.get(0);
                    this.readyQueue.remove(0);
                    this.running.run();
                }
            }
            else
            {
                this.running.run();
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
