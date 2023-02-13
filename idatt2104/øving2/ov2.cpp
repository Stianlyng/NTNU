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
        
        Workers(int num_threads) : num_threads(num_threads) {}
        
        void start()
        {
            for (int i = 0; i < num_threads; ++i)
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
                                    task = *tasks.begin();
                                    tasks.pop_front();
                                    break;
                                }
                                if (!run)
                                {
                                    return;
                                }
                                cv.wait(lock);
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

    void post(function<void()> task)
    {
        {
            lock_guard<mutex> lock(mutex_lock);
            tasks.push_back(task);
        }
        cv.notify_one();
    }

    void join()
    {
        for (auto& thread : threads)
        {
            thread.join();
        }
    }

    void post_timeout(function<void()> task, int timeout)
    {
        thread([this, task, timeout] {
            this_thread::sleep_for(chrono::milliseconds(timeout));
            post(task);
        }).detach();
    }

    void stop()
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
