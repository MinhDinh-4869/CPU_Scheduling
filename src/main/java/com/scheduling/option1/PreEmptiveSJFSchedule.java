/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
package com.scheduling.option1;

import java.util.List;

public class PreEmptiveSJFSchedule extends Schedule implements ScheduleInterface{
    public PreEmptiveSJFSchedule(int resource_num)
    {
        super(resource_num);
    }


    public void startProcess()
    {
        while(true)
        {
            System.out.println("#Time = " + this.time);
            addWaitToReady();
            sortBurst(this.readyQueue);

            showReadyQueue();

            initState();//for drawing chart
            scheduleCPU();
            scheduleResource();

            if(this.readyQueue.size()== 0 &&  resourceIsOut())
                break;
            this.time++;
            System.out.println("--------------------------------");
        }
    }



    void scheduleCPU()
    {
        if(readyQueue.size() > 0) {
            if (this.readyQueue.get(0).getBurst() == 0) {
                this.readyQueue.get(0).removeDoneBurst();
                if (this.readyQueue.get(0).canJump) {
                    //move to the appropriate resource
                    //this.resourceQueue.get(readyQueue.get(0).resource_id).add(readyQueue.get(0));
                    this.resourceQueue.get(readyQueue.get(0).resource_id.get(0)).add(readyQueue.get(0));
                    this.readyQueue.remove(0);
                } else {
                    System.out.println(this.readyQueue.get(0).name + " exit System!");
                    this.readyQueue.get(0).setOutTime(this.time - 1);
                    this.readyQueue.remove(0);
                }

                if (this.readyQueue.size() > 0) {
                    this.readyQueue.get(0).run();
                    this.readyQueue.get(0).rollBackWaitTime();
                }
            } else {
                //let the first process in the queue runs
                this.readyQueue.get(0).run();
                this.readyQueue.get(0).rollBackWaitTime();
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
