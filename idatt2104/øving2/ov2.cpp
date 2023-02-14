#include <iostream>
#include <vector>
#include <functional>
#include <list>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <atomic>


using namespace std;

class Workers {

public:
    private:
        int num_threads;
        vector<thread> threads;
        list<function<void()>> tasks;
        mutex mutex_lock;
        condition_variable cv;
        atomic<bool> run{true};
    
    public:
        
        Workers(int num_threads) : num_threads(num_threads) {} // Initializes with the number of threads
        
        void start()
        {
            for (int i = 0; i < num_threads; ++i) // Creates the threads
            {
                threads.push_back(thread([&] { 
                    while (true)
                    {
                        function<void()> task;
                        {
                            while (true)
                            {
                                unique_lock<mutex> lock(mutex_lock);
                                if (!tasks.empty())
                                {
                                    task = *tasks.begin(); // copies the task
                                    tasks.pop_front(); // removes the task from the list
                                    break;
                                }
                                if (!run)
                                {
                                    return;
                                }
                                cv.wait(lock); // waits for a task to be posted
                            }
                        }
                            /*
                            function<void()> task; {
                                unique_lock<mutex> lock(mutex_lock);
                                cv.wait(lock, [&] { return !tasks.empty(); });
                                task = tasks.front();
                                tasks.pop_front();
                            }
                            */
                        task();
                    }
                }));
            }
        }

    void post(function<void()> task) // Posts a task to the queue
    {
        {
            lock_guard<mutex> lock(mutex_lock); // Locks the mutex
            tasks.push_back(task); // Adds the task to the list
        }
        cv.notify_all();    // Notifies one thread that a task has been posted 
                            // Should i use notify_all() instead?                   ?
    }

    void join() // Calls join() on all the threads
    {
        for (auto& thread : threads)
        {
            thread.join();
        }
    }

    void post_timeout(function<void()> task, int timeout) // Posts a task to the queue after a timeout
    {
        thread([this, task, timeout] {
            this_thread::sleep_for(chrono::milliseconds(timeout)); // Sleeps for the timeout
            post(task);
        }).detach(); 
    }

    void stop() // Stops the threads
    {
        run = false;
        cv.notify_all();
    }

};

int main()
{
    Workers worker_threads(4);
    Workers event_loop(1);

    worker_threads.start(); // Create 4 internal threads
    event_loop.start(); // Create 1 internal thread


    worker_threads.post([] {
        cout << "Task A" << endl;
    });

    worker_threads.post([] {
        cout << "Task B" << endl;
    });

    event_loop.post_timeout([] {
        cout << "task C" << endl;
    }, 2000); 

    event_loop.post_timeout([] {
    cout << "task D" << endl;
    }, 1000); 
    
    this_thread::sleep_for(chrono::milliseconds(3000));

    worker_threads.stop(); // Stop the worker threads
    event_loop.stop(); // Stop the event thread

    worker_threads.join(); // Calls join() on the worker threads
    event_loop.join(); // Calls join() on the event thread

}
