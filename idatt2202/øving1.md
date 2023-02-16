# 1 The process abstraction
### 1. Briefly describe what happens when a process is started from a program on disk. A mode switch from  kernel- to user-mode must happen.
- **Explain why this is necessary**
	- The operating system assigns a PID value and inserts it in the primary process table.
	- The  PCB are initialized with all the required memory spaces allocated
	- A sequence of instructions is stored in memory.
	- The memory address of the first instruction is copied to the instruction pointer.
	- The context engine executes the instruction specified by the instruction pointer. And increments the pointer for the execution of the next instruction
> The **User mode** is normal mode where the process has limited access.
>
> While the **Kernel mode** is the privileged mode where the process has unrestricted access to system resources like hardware, memory, etc.


- For security and compatibility reasons this means the switch is necessary to be able to run the new process.

### 2. Download the latest Linux kernel source code from https://kernel.org and unpack it. Use a web search engine to help identify the file in the source tree that contains the process descriptor structure
> hint: its name is task struct)

A quick search on botlin returned:
```python
include/linux/sched.h, line 726 (as a struct)
```

#### (a) Stores the process ID
  
**Answer:** PID (Process identifier)

#### (b) Keeps track of accumulated virtual memory  
  
CTRL + F in sched.h shows:
  
```C
/* Accumulated virtual memory usage: */
u64acct_vm_mem1;
  
//Answer: acct_vm_mem1
```

# 2 Process memory and segments  
  
The memory region allocated to a process contains the following segments.
  - Text segment
  - Data segment
  - Stack
  - Heap

### 1. Sketch the organisation of a process’ address space. Start with high addresses at the top, and the lowest address (0x0) at the bottom.
  
![[Drawing 2022-09-16 16.46.29.excalidraw]]
    
### 2. Briefly describe the purpose of each segment. 
  
![[Drawing 2022-09-16 17.28.48.excalidraw]]
  
#### Why is address 0x0 unavailable to the process?
  - Reserved for the null pointer
  - Detects null pointer errors

### 3. What are the differences between a global, static, and local variable?

- **Local variable**
	- Declared inside a function or a block
	- Its scope is always limited to that particular function or block.
	- It remains alive until the block or function completes its execution.
- **Global variable**
	- Accessible to entire program
	- Available to all the functions or blocks.
	- It can be accessible to all the functions in a program.
- **Static variables**
	- Has a property of preserving their value even after they are out of their scope
	- Preserve their previous value in their previous scope and are not initialised again in the new scope.

### Given the following code snippet, show which segment each of the variables (var1, var2, var3) belong to.
  
  ```C
#include <stdio.h> #include <stdlib.h>
  
  int var1 = 0;
  
  void main()
  
  {
  
  int var2 = 1;
  
  int *var3 = (int *)malloc(sizeof(int)); 
  // Note, since we are using malloc(), var3 will be a
  // pointer into the heap!
  // So the question is, where is the pointer stored?
  
  *var3 = 2;
  
  printf("Address: %x; Value: %d\n", &var1, var1);
  
  printf("Address: %x; Value: %d\n", &var2, var2);
  
  printf("Address: %x; Address: %x; Value: %d\n", &var3, var3, *var3);
  
  }
  ```
  
- **Var1**
  - Is a global variable,  it therefore belongs to the data segment
- **Var2**
  - Is a local varable inside a function, it therefore belongs to the stack
- **Var3**
  - As mentioned in the comments, it is a pointer into the heap.
  - Var3 is still stored in the stacks just as var 2, since its a local variable stored inside a function.
  - The data however points to the heap, as a result of calling malloc. Which allocates the requested memory and returns a pointer to it.

# 3 Program code
### 3.1 Compile the example given above using gcc mem.c -o mem. Determine the sizes of the text, data, and bss segments using the command-line tool size.
  
```bash
root@debian-gnu-linux-11:~# size ovning1
text   data    bss    dec    hexfilename
2062    648      8   2718    a9eovning1

```
  
### 3.2 Find the start address of the program using objdump -f mem.
  
```bash
root@debian-gnu-linux-11:~# objdump -f ovning1

ovning1:     file format elf64-littleaarch64
architecture: aarch64, flags 0x00000150:
HAS_SYMS, DYNAMIC, D_PAGED
start address 0x00000000000006a0

```
  
### 3.3 Disassemble the compiled program using objdump -d mem. Capture the output and find the name of the function at the start address. Do a web search to find out what this function does, and why it is useful.
  
```bash
root@debian-gnu-linux-11:~# objdump -d ovning1  | grep 6a0
00000000000006a0 <_start>:
			   6a0:d280001d movx29, #0x0                   // #0
```
  
>[!INFO]
> For most C and C++ programs, the true entry point is not `main`, it’s the `_start` function. This function initializes the program runtime and invokes the program’s `main` function.
> 
> Source: https://embeddedartistry.com/blog/2019/04/08/a-general-overview-of-what-happens-before-main/


### 3.4 Run the program several times (hint: running a program from the current directory is done using the syntax ./mem). The addresses change between consecutive runs. Why?
  
**It signifies that your program is being loaded a different (virtual) address each time you run it**. This is a feature called Address Space Layout Randomization (ASLR) and is a feature of most modern operating systems. Its purpose is to make it more difficult to exploit security holes.
  
# 4 The stack

Consider the following C program:
  
  ```C
#include <stdio.h> #include <stdlib.h>
  
  void func() {
  
  int localvar = 2;
  
  printf("func() with localvar @ 0x%08x\n", &localvar);
  
  printf("func() frame address @ 0x%08x\n", __builtin_frame_address(0));
  
  localvar++;
  
  func();
  
  }
  
  int main() {
  
  printf("main() frame address @ 0x%08x\n", __builtin_frame_address(0));
  
  func();
  
  exit(0);
  
  }
  ```

### 1. Compile the example given above using gcc stackoverflow.c -o stackoverflow.
### 2. Determine the default size of the stack for your Linux system. Hint: use the ulimit command 
  
>(a websearch or running the command ulimit --help will help find the appropriate command-line flags).
  
```bash
root@debian-gnu-linux-11:~# ulimit -a | grep stack
stack size                  (kbytes, -s) 8192
```
  
The default stack size is 8MiB

### 3. Run the program. Describe your observations and find the cause of the error.
- Got a segmentation fault error
  - Which occurs when a program attempts to access a memory location that it is not allowed to access.
- The stack overflowed ie. ran out of space. As the filename indicates ;)
- Its also evident looking at the sourcecode that there is a recursive call in func() that does not have a base case.
### 4. Run the program and pipe the output to grep and wc -l:
  
```bash
./stackoverflow | grep func | wc -l

```
#### OUTPUT
```bash
root@debian-gnu-linux-11:~# ./stackoverflow | grep func | wc -l
523926

```
- The wc utility displays the number of lines, words, and bytes contained in each input file, or standard input (if no file is specified) to the standard output.

### What does this number tell you about the stack? How does this relate to the default stack size you found using the ulimit command?
- When using grep to filter the results, it indicates the number of times func gets called.
- The output needs to be divided by two since its written two times

### 5. How much stack memory (in bytes) does each recursive function call occupy?

Since the default stackspace was 8Mib, one can estimate the size of the each recursive function by:
$$\frac{\text{Stack size}}{\text{number of calls}} \implies \frac{8192KiB \cdot 2024}{\frac{523926}{2}}  \approx 32 \ bytes $$