#include <iostream>
#include <vector>
#include <functional>
#include <list>
#include <mutex>
#include <thread>
#include <condition_variable>

class Workers
{
public:
    Workers(int num_threads)
        : num_threads_(num_threads)
    {
    }

    void start()
    {
        for (int i = 0; i < num_threads_; ++i)
        {
            threads_.push_back(std::thread([this] {
                while (true)
                {
                    std::function<void()> task;
                    {
                        std::unique_lock<std::mutex> lock(mutex_);
                        cv_.wait(lock, [this] { return !tasks_.empty(); });
                        task = tasks_.front();
                        tasks_.pop_front();
                    }
                    task();
                }
            }));
        }
    }

    void post(std::function<void()> task)
    {
        {
            std::lock_guard<std::mutex> lock(mutex_);
            tasks_.push_back(task);
        }
        cv_.notify_one();
    }

    void join()
    {
        for (auto& thread : threads_)
        {
            thread.join();
        }
    }

private:
    int num_threads_;
    std::vector<std::thread> threads_;
    std::list<std::function<void()>> tasks_;
    std::mutex mutex_;
    std::condition_variable cv_;
};

int main()
{
    Workers worker_threads(4);
    Workers event_loop(1);
    worker_threads.start(); // Create 4 internal threads
    event_loop.start(); // Create 1 internal thread

    worker_threads.post([] {
        std::cout << "Task A" << std::endl;
    });

    worker_threads.post([] {
        std::cout << "Task B" << std::endl;
    });

    event_loop.post([] {
        std::cout << "Task C" << std::endl;
    });

    event_loop.post([] {
        std::cout << "Task D" << std::endl;
    });

    worker_threads.join(); // Calls join() on the worker threads
    event_loop.join(); // Calls join() on the event thread

    return 0;
}
