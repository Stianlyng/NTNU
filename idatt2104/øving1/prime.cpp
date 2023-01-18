#include <iostream>
#include <mutex>
#include <thread>
#include <vector>
#include <algorithm>    


using namespace std;

bool isPrime(int x) {
    
    if (x<= 1) {
        return false;
    }
    
    // return true if x is prime, false otherwise
    for (int i = 2; i < x; i++) {
        if (x % i == 0) {
            return false;
        }
    }
    return true;
}


int main() {
    vector<int> sum;
    mutex sum_mutex; 

    thread t1([&sum, &sum_mutex] {
        for (int i = 0; i < 49; i++) {
            if (isPrime(i) == true) {
                unique_lock<mutex> lock(sum_mutex); 
                sum.push_back(i);
            }
        }
    });
    
    thread t2([&sum, &sum_mutex] {
        for (int i = 50; i < 100; i++) {
            if (isPrime(i) == true) {
                unique_lock<mutex> lock(sum_mutex); //
                sum.push_back(i);
                
            }
        }
    });

t1.join();
t2.join();

sort(sum.begin(), sum.end());
//liste av thread vector

for (int i = 0; i < sum.size(); i++) {
    cout << sum[i] << endl;
}

    
}