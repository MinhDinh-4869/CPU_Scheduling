package com.scheduling.option1;

import java.util.List;

public class PreEmptiveSJFSchedule extends Schedule{
    public PreEmptiveSJFSchedule()
    {
        super();
    }


    public void startProcess()
    {
        while(true)
        {
            System.out.println("#Time = " + this.time);
            addWaitToReady();
            sortBurst(this.readyQueue);

            showReadyQueue();
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
                    this.resourceQueue.get(readyQueue.get(0).resource_id).add(readyQueue.get(0));
                    this.readyQueue.remove(0);
                } else {
                    System.out.println(this.readyQueue.get(0).name + " exit System!");
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

    /*
    void scheduleResource()
    {
        for(List<Process> l : this.resourceQueue)
        {
            if(l.size() > 0) {
                if (l.get(0).getResourceBurst() == 0) {
                    l.get(0).removeDoneResourceBurst();//remove the done resource burst from the process resource queue
                    if (l.get(0).canJump) {
                        this.readyQueue.add(l.get(0));
                        l.remove(0);
                    } else {
                        System.out.println(l.get(0).name + " exit System!");
                        l.remove(0);
                    }

                    if (l.size() > 0) {
                        l.get(0).runResource();
                    }
                } else {
                    l.get(0).runResource();
                }
            }
        }
    }
*/

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
                        l.remove(0);
                    }
                }
            }
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
}
