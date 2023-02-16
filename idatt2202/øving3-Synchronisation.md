###### By: Stian Lyng Stræte
# 1. Synchronisation

## 1. The principle of process isolation in an operating system means that processes must not have access to the address spaces of other processes or the kernel. However, processes also need to communicate.

### (a) Give an example of such communication.

- Allowing a program use of an internetsocket (ex. printer) while still restricting access to a file, folder or diskdrive.
- An example could be making a SQL query against a server
	- Passing  "SELECT * FROM table" using a IPC protocol. 
	- The SQL server does all the work to get the results from the database, and passes the results back to you also via IPC.

### (b) How does this communication work?

- Communication in a system with process isolation is allowed over inter-process communication (IPC). 
- There's pretty much an unlimited number of ways processes communicate with each other trough the IPC, but the most common are trough channels such as:
	- **Shared memory**
		-  Both processes can be set up to read / write to a common space in memory so if one process leaves a note, the other can read it and do something with it.
	- **Network stack**
		- **Local sockets** 
			- If both processes open a TCP/IP socket, they can talk to each other. 
		- **Internet sockets** 
			- The TCP/IP socket mentioned above can be extended to work for processes running on different computers

### (c) What problems can result from inter-process communication?
- I started writing a full length answer on this one until i noticed the later questions regarding race conditions. I moved the answers down and to keep it simple..  
- Problems such as:
	- Race conditions
	- Synchronisation issues

## 2. What is a critical region? Can a process be interrupted while in a critical region? Explain.
- A critical section allows only one process to access the protected values at a time, making sure it can not be changed by another thread during this time.
- Regions of code that would not function properly if they happened simultaneously. Most likely a a result of referencing or manipulating the state of the shared resources
- Because a process with access to a critical region holds a lock to said region, it isn't a huge deal if it's interrupted. No other process can access the region while the lock holder sleeps, which means that the operations performed on the critical section still appears atomic. Interrupts are usually disabled for kernel processes accessing critical regions, though.

## 3.  Explain the difference between busy waiting (polling) versus blocking (wait/signal) in the context of a process trying to get access to a critical section.

- The difference between the two is what the application does between polls.
	- If a program polls in a timed intervall, and does something else in the mean time, it's polling.  
	- If the program continuously polls without doing anything in between checks, it's called a busy-wait.


## 4. What is a race condition? 
A race condition is when a systems behavior is dependent on the sequence or timing of other uncontrollable events.

### Give a real-world example.

- Imagine you got a string variable on a website, and the variable is shared between two threads.
- The first thread begins writing the string slowly, one character at a time. 
	- While this is happening, the second thread starts reading the shared variable, but much faster than the first thread is writing it.
- The second thread ends up with an incomplete string because it started reading the contents of the shared variable before first thread is done writing it.

##### Example from wikipedia
| ex 1                                                                                | ex 2                                                                                                                   |
| ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Assume that two threads each increment the value of a global integer variable by 1. | If the two threads run simultaneously without locking or synchronization, the outcome of the operation could be wrong. |
| Ideally, the following sequence of operations would take place:                     |                                                                                                                        |
| ![[Pasted image 20221104134043.png\|300]]                                           | ![[Pasted image 20221104134107.png\|300]]                                                                              |
| In the case shown above, the final value is 2, as expected                          | In this case, the final value is 1 instead of the expected result of 2.                                                |
|                                                                                     |  This occurs because here the increment operations are not mutually exclusive. Mutually exclusive operations are those that cannot be interrupted while accessing some resource such as a memory location.                                                                                                                      |

## 5.  What is a spin-lock, and why and where is it used?

- When you use locks such as critical regions and others. The OS puts the thread in the WAIT state and preempts it by scheduling other threads on the same core. 
	- In some cases the wait time can be really short, and using the regular locks will result in bad performance, having to wait for preemption to recieve CPU time again.
	- Spinlocks don't cause this preemption but wait in a loop till the other core releases the lock.
	- 
- Spin-locks are commonly used in the Linux kernel to provide safe and efficient syncronization between the kernel threads
	- Spinlocks in linux kernel - https://www.linuxjournal.com/article/5833

# 2. Deadlocks
>A deadlock is like 2 people who are too shy to poop near others sitting in adjacent bathroom stalls, both waiting for the other to leave.
>
>_Sr Dev from reddit_
## 1. What is the difference between resource starvation and a deadlock?

- **Deadlock**
	- Happens when the processes holds a resource and waits for another process to hold another resource. 
	- ie. A state in which each member of a group of actions, is waiting for some other member to release a lock.
- **Starvation**
	- Closely related to deadlocks and livelocks
	- In a computer system there will be countless requests for resources. 
		- To keep things running in a good way, these requests needs to be managed and prioriticed.
		- Starvation happens when a low priority program requests a system resource but cannot run because a higher priority program has been employing that resource for a long time.
		- This may lead to some processes never getting serviced, even if they are deadlocked

## 2. What are the four necessary conditions for a deadlock? Which of these are inherent properties of an operating system?

-  Mutual Exclusion
-  Hold and Wait
-  No preemption
-  Circular wait

> Limited resources are inherent properties of an operating system



## 3.  How does an operating system detect a deadlock state? What information does it have available to make this assessment?

- The OS detects deadlocks through the Resource Allocation Graph(RAG)
	- The RAG is the complete information about all the processes which are holding some resources or waiting for some resources. 
	- With this information the OS can decide if a resource cycle is being formed.

# 3 Scheduling
## 1. Uniprocessor scheduling

### (a) When is first-in-first-out (FIFO) scheduling optimal in terms of average response time? Why?

- **FIFO schedules tasks in the order they arrive**
	- Is simple and minimises overhead.
- **If tasks are variable in size, then FIFO can have  very poor average response time**.
	- **Imagine having two processes:**
		- With FIFO scheduling the first process will completely execute before the next begins execution. 
		- This will cause a delay in execution on the second process based on the size and operations that the first has to do.
		- Using a **round robin** method is in this case a better alternative
- **If tasks are equal in size, FIFO is optimal in terms of average response time.**
	- FIFO scheduling is also a good alternative if the processes are small enough to complete in a single cycle of the round robin scheduler, where the overhead of the also requires resources.

### (b) Describe how Multilevel feedback queues (MFQ) combines first-in-first-out, shortest job first, and round robin scheduling in an attempt at a fair and efficient scheduler. What (if any) are its shortcomings?

- **The MFQ is a scheduling algorithm the extends the standard algorithms (FIFO,SJF,RR ) allowing processes to move between queues**
- The ready queue is partitioned into smaller queues on the basis of the CPU burst characteristics.
- The differences queues are classified by high/low priority, in contrast to the MLQ which classifies the queue into two groups (foreground/background)
- The MFQ keeps analysing the execution time of processes and changes its priority and thereby queue based on this analysis.
	- This prevents starvation
- Allowing the smaller processes to move before the larger ones gives:
	- A flexible scheduler
	- It high scheduling overhead in contrast to MLQ which has low scheduling overhead and is inflexible
- MFQ is also the more complex algorithm

## 2. Multi-core scheduling
### (a) Similar to thread synchronisation, a uniprocessor scheduler running on a multi-core system can be very inefficient. Explain why (there are three main reasons). Use MFQ as an example.

- Contention on MFQ lock
	- Bottleneck
- Cache coherence overhead
	- MFQ data structure would ping between caches to fetch the MFQ state
	- Fetching data from other caches can be really slow
- Limited cache reuse
	- Data from the last run might be stored in its old cache.
### (e) Explain the concept of work-stealing.

![[Pasted image 20221104182450.png|400]]
- It solves to problem of all cores not doing their work while work is to be done. 
- If a core is sitting idle and wasting potential performance with its empty queue. 
- It checks the work queues of the other cores and "steal" their work. 
