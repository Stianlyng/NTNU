# Processes and threads 
#### by: Stian Lyng Stræte
## 1. Explain the difference between a process and a thread.

A process is an object that is scheduled for an execution by the OS. The individual processes has its own memory space, with a scope "of its own" which keeps it separated from other processes.

A thread is more or less has a multi-tasking functionality for the processes. One process can have multiple threads. And with their own sequence of execution, which naturally allows for more work to get done, depending on how many simultaneous task a program requires. 

And important distinction is:
When memory is allocated to a process, all the threads which belong to it get access its memory. 

This means that if a user where to kill a program, its process dies, and all its threads go in the grave with it. Not the other way around

## 2. Describe a scenario where it is desirable to:

### Write a program that uses threads for parallel processing

Before the Covid pandemic hit us all in the forehead i used to work as a videographer.

My time as a videographer gave me some insight  in the importance of threads. I would often edit and process videos with hours of length, and believe me - editing raw footage is a hassle. It's a known fact to go for a computer with as many cores and threads as possible. 

As the codecs became more and more complex, the computational power de/encoding increased fast. Most of the encoders such as the H.256 Encoder, takes advantage of multiple parallel threads to be used.  

**Processing without multi-threading:**
- Happen sequentially
- Which means processing the current frame would have to finish, before reading the next.
- The biggest chunk of time it takes is to process the video is the actual transfer/request for each frame to memory. And not the actual computation from the CPU or GPU

**Processing with multithreading**
- Not required to be sequential
- Allows for one thread to handle the task of reading frames, while the main thread can use the CPU or GPU.

### Write a program that uses processes for parallel processing

Researching this concept i came over Cromiums own blog post on the subject. My writing might be influenced by their talking points. 

- https://blog.chromium.org/2008/09/multi-process-architecture.html

With time websites with active code in them has grown immensely. Most of the software we had as native apps earlier has now moved to the web. The IDE i'm writing this assignment is an electron based program which essentially is a webapp inside a Chomium browser.

As when before it made sense to render the native apps in different processes, it now makes sense to do the same with browsers. 

A different and possibly more important aspect is the separation or isolation between the different sites, and the security benefits this yields. The web browser is in most cases the most vulnerable point of attack on the computer. With a spiders-web of active code in both websites and plugins, isolation is a must.

## 3. Explain why each thread requires a thread control block (TCB).
It contains important attributes required for allocating a processors power to the thread. 

**The TCB contains**:
-   Thread ID
-   The contents of the CPU Register
-   Instructions pointer
-   Scheduling Information
-   Thread state 
-   Stack Pointer
-   Signal Mask
-   Thread Parameters like:
	- Start function
	- Stack size.

## 4.What is the difference between cooperative threading and pre-emptive threading? 

**Briefly describe the steps necessary for a context switch for each case.**

Thread switching is the most common form of dispatching a thread, that is, of causing a processor to execute it. The only way a thread can be dispatched without a thread switch is if a processor is idle.

In **cooperative threading** each thread’s program contains explicit code at each point where a thread switch should occur. Which gives room for one threads buggy code to hold all the others up.

> **Excerpt from the syllabus:**
> Consider, for example, a loop that is expected to iterate only a few times; it would seem safe, in a cooperative multitasking system, to put thread switches only before and after it, rather than also in the loop body. However, a bug could easily turn the loop into an infinite one, which would hog the processor forever. With preemptive multitasking, the thread may still run forever, but at least from time to time it will be put on hold and other threads allowed to progress.

As mentioned in the excerpt, **preemptive threading** give some advantages. The programs's code does not contain any thread switches. It can be viewed as a multitask execution, where the programs cooperates with each other sharing a processors runtime between themselfes. The thread switches will be automatically performed when they are needed.  

## C program with POSIX threads

*nix operating systems use POSIX threads, which are provided by the pthread library. Consider the following adapted code from the textbook (the code has been modified slightly to use pthread, while the book assumes its own thread implementation).


```C
#include <stdio.h>
#include <pthread.h>
#define NTHREADS 10 pthread_t threads[NTHREADS];
void *go (void *n) {
	printf("Hello from thread %ld\n", (long)n); 
	pthread_exit(100 + n);
	// REACHED?
}

int main() {

    long i;

    for (i = 0; i < NTHREADS; i++)  pthread_create(&threads[i], NULL, go, (void*)i);

    for (i = 0; i < NTHREADS; i++) {

        long exitValue;

        pthread_join(threads[i], (void*)&exitValue);
		printf("Thread %ld returned with %ld\n", i, exitValue);

    }
    printf("Main thread done.\n");
	return 0; 
}

```

**We can compile the code and tell the compiler to link the pthread library:**

```bash
$ gcc -o threadHello threadHello.c -lpthread
```

### At the command prompt, run the program using ./threadHello. The program gives output similar to the following:


```bash
Hello from thread 0
Hello from thread 3
Hello from thread 5
Hello from thread 1
Hello from thread 4
Thread 0 returned with 100
Thread 1 returned with 101
Hello from thread 9
Hello from thread 8
Hello from thread 2
Hello from thread 7
Hello from thread 6
Thread 2 returned with 102
Thread 3 returned with 103
Thread 4 returned with 104
Thread 5 returned with 105
Thread 6 returned with 106
Thread 7 returned with 107
Thread 8 returned with 108
Thread 9 returned with 109
Main thread done.
```

#### Study the code and the output. Run the code several times. Answer the following questions.
### 1. which part of the code (e.g., the task) is executed when a thread runs? Identify the function and describe briefly what it does.
- The function go gets executed for each thread
- Prints out the 'Hello from' line
- Returns with 100 + $n$, where $n$ is thread id created in pthread.create 
### 2. Why does the order of the “Hello from thread X” messages change each time you run the program?
- The OS's scheduler is influenced by many factors ie:
	- What other processes are doing
	- What the hardware is doing
 In other words. Even though the scheduling of he threads should be ordered in the order of creation, the actual scheduling is unpredictable.
### 3. What is the minimum and maximum number of threads that could exist when thread 8 prints “Hello”?
- First the program creates a thread to run the script
- Then `NTHREADS` (defined by 10) creates the defined number of threads
	- The threads chould finish their operation before thread 8 prints, but the actual mean thread is still operating.
- This means that the minimum is 2 threads
- And the maximum is the 10 threads created by `NTHREADS` in addition to the first main thread

$$min=2 \ \ \ \ max=11$$

### 4. Explain the use of pthread join function call.
The `pthread_join` function, functions as a mean to suspend the execution of the calling thread until target thread has already teminated. 

After a sucessful call it returns an argument that is `not null` which indicates the target thread has been terminated.

This results in the threads waiting in order
### 5. What would happen if the function go is changed to behave like this:

```C
void *go (void *n) {

         printf("Hello from thread %ld\n", (long)n);

         if(n == 5)

sleep(2); // Pause thread 5 execution for 2 seconds pthread_exit(100 + n);

// REACHED?

}
```

As mentiones in the task above one has to wait for the `pthead_join`  to return. In this case there would be a 2 second delay while waiting for thread 5 to finish.

### 6. When pthread join returns for thread X, in what state is thread X?
The actual return value mantioned above the `pthread_join` is terminated if the function returns `not null`. 

By looking a little further you can see that if `pthread_join` returns `not null` this means that the function has called `pthread_exit()` which in turn returns exit status with the value of 0 if the termination was succesful.

$$\text{Exit status:}$$
$$0 = succes$$
$$1 = failure$$
$$succues = finished$$

