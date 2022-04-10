package com.scheduling.option1;

import java.util.List;

public class NonPreEmptiveSJFSchedule extends Schedule{
    public NonPreEmptiveSJFSchedule()
    {
        super();
    }

    private Process running = null;

    public void startProcess()
    {
        while(true)
        {
            System.out.println("#Time = " + this.time);
            addWaitToReady();
            showReadyQueue();

            scheduleCPU();
            scheduleResource();
            if(this.running == null && this.readyQueue.size() == 0 &&  resourceIsOut())
                break;
            this.time++;
            System.out.println("-------------------------");
        }
    }


/*
    void scheduleCPU()
    {
        if(this.readyQueue.get(0).getBurst() == 0)
        {
            this.readyQueue.get(0).removeDoneBurst();
            if(this.readyQueue.get(0).canJump){
                this.resourceQueue.get(readyQueue.get(0).resource_id).add(readyQueue.get(0));
                this.readyQueue.remove(0);
            }
            else{
                System.out.println(this.readyQueue.get(0).name + " exit System!");
                this.readyQueue.remove(0);}

            if(this.readyQueue.size() > 0)
            {
                this.readyQueue.get(0).run();
            }
        }
        else
        {
            //let the first process in the queue runs
            this.readyQueue.get(0).run();
        }
    }
 */

    /*
    void scheduleCPU()
    {
        //assign the first
        if (this.running == null && this.readyQueue.size() > 0)
        {
            sortBurst(this.readyQueue);
            this.running = this.readyQueue.get(0);
            this.readyQueue.remove(0);
            this.running.run();
        }
        else if (this.running != null) {
            if (this.running.getBurst() == 0) {
                this.running.removeDoneBurst();
                if (this.running.canJump) {
                    this.resourceQueue.get(this.running.resource_id).add(this.running);
                } else {
                    System.out.println(this.running.name + " exit System!");
                }
                this.running = null;

                if (this.readyQueue.size() > 0) {
                    sortBurst(this.readyQueue);
                    this.running = this.readyQueue.get(0);
                    this.readyQueue.remove(0);
                    this.running.run();
                }
            } else {
                this.running.run();
            }
        }
    }
*/
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
                    this.resourceQueue.get(this.running.resource_id).add(this.running);
                } else {
                    System.out.println(this.running.name + " Exit System!");
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
    void moveToReady(List<Process> resource)
    {
        if(resource.get(0).canJump)
        {
            this.readyQueue.add(resource.get(0));
            resource.remove(0);
        }
        else
        {
            System.out.println(resource.get(0).name + " Exit System");
            resource.remove(0);
        }
    }
    void scheduleResource()
    {
        for(List<Process> l : this.resourceQueue)
        {
            if(l.size() > 0) //there is process in resourceQueue
            {
                //run
                if(l.get(0).getResourceBurst() > 0)
                {
                    l.get(0).runResource();
                    //after run, check the resource for being 0
                    //if yes, move it to ready queue for the next second
                    //The cpu scheduling phase does not need this because we set the resource scheduling to be the end
                    //phase, it needs to prepare for the next loop
                    if(l.get(0).getResourceBurst() == 0)
                    {
                        l.get(0).removeDoneResourceBurst();
                        moveToReady(l);
                    }
                }
                else if(l.get(0).getResourceBurst() == 0) //if the resource burst is out
                {
                    l.get(0).removeDoneResourceBurst();
                    /*
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
                    */
                    moveToReady(l);
                    if(l.size() > 0)
                    {
                        l.get(0).runResource();
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
