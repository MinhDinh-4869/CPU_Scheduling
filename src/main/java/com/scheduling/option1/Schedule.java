package com.scheduling.option1;

import java.util.ArrayList;
import java.util.List;

public abstract class Schedule {
     public int time = 0;
     protected List<Process> readyQueue = new ArrayList<>();
    //set the number of resources to be 3
     protected List<List<Process>> resourceQueue = new ArrayList<>(3);
     protected List<Process> waitQueue = new ArrayList<>();
     public Schedule()
     {
         for(int i= 0; i< 3; i++)
         {
             this.resourceQueue.add(new ArrayList<>());
         }
     }

    void add(Process p)
    {
        this.waitQueue.add(p);
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

    public abstract void startProcess();
}
