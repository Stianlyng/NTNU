###### by: Stian Lyng Stræte
> BTW: The word that looks like links are actually `[[wikilinks]]` that i use to organise my notes system (not copy paste from the web 😅)

# 1 File systems  

## 1. Name two factors that are important in the design of a file system.  

- The goal of a file system is allowing logical groups of data to be organised into files, that can be manipulated. 
- The file system defines how files are named, stored and retrieved. 

### Important factors

- **Performance** - Considering the best combination of structures and algorithms for the specific use-case: 
	- Managing disk media differs from differs i comparison to some other components(ex. memory management):
		- The time it takes to access different locations on the disk varies. (Seeking is a performance heavy operation)
		- Data can only be written in fixed sized chunks
		- The data needs maintaining on a regular basis
- **Reliability** - Ensuring that the data is safe, given the unreliable nature of storage devices
	- Detecting corruption such as [[section errors]], [[lost writes]], or [[misdirected writes]], and deciding which data integrity and protection techniques one should implement.
		- Checksums is the primary function for preserving data integrity today. The [[checksum]] is small summary( 4 - 8 Bytes ) of the computation of the chunk of data as input. 

## 2. Name some examples of file metadata.

Metadata is data about data, and is stored in the [[inodes]] (information nodes) in the [[UNIX]] file systems.

-** The [[inodes]] stores metadata such as:**
	- The disk block numbers holding that file’s data
	- Access permissions
	- File size
	- Owner
	- Creation/modification times
	- locks

# Files and directories  
## 1. Consider a Fast File System (FFS) like Linux’s ext4.  

### (a) Explain the difference between a hard link and a soft link in this file system. What is the length of the content of a soft link file?  

- The difference between the two comes down to what they point to, or reference.
	- **[[Hard links]]**
		- Points to a specific space on the hard drive. 
		- Is a directory entry that associates a pathname with a file trough its ==disk block number?==.
			- A file must have at-least one hard link
			- Multiple hard links to a file is also possible
				- Causes an [[alias effect]] where a process can open the file via multiple paths and modify its content.
				- Which means you would need to remove all the hard links to be able to delete the file.
			- Creates a hierarchical file system with a tree structure
				- Multiple hard links to directories however, could create a circular directory structure if not done properly.
	- **[[Soft links]] or [[Soft links|symlink]]**
		- Is not a direct link to the file itself
			- Instead it points to or references a hard link or another soft link.
		- It exists independently of its target
			- If the symlink is deleted the target remains unaffected
			- If the hard link is deleted the symlink continues to exist, which causes:
				- broken, dangling, orphaned or dead links
			- Changes an otherwise hierarchic filesystem from a tree into a directed graph


![[Pasted image 20221116225609.png]]

### (b) What is the minimum number of references (hard links) for any given folder?  

One reference (explained in task a)

### (c) Consider a folder /tmp/myfolder containing 5 subfolders. How many references (hard links) does it have? Try it yourself on a Linux system and include the output. Use ls -ld /tmp/myfolder to view the reference count (hint, it’s the second column in the output).  
```bash
parallels@debian-gnu-linux-11:/tmp/myfolder$ ls -ld
parallels@debian-gnu-linux-11:/tmp/myfolder$ man ls
parallels@debian-gnu-linux-11:/tmp/myfolder$ info '(coreutils) ls invocation'
drwxr-xr-x 7 parallels parallels 4096 Nov 17 15:52 .
```

The second field shows that `myfolder` has 7 references 

### (d) Explain how spatial locality is achieved in a FFS.  
- **[[FFS]] introduced the idea of a [[disk-aware layout]]**
	- The [[cylinder group]]
		- FFS divides the disk into a number of cylinder groups
		- FFS aggregates $N$ consecutive cylinder groups, thus the entire disk can be viewed as a collection of groups
		- Has the ability to place files and directories into a group, and track all necessary information about them
			- This makes it possible to Include all the structures you might expect a file system to have within each group. F.eks:
			- Space for inodes
			- Data blocks
			- Structures to track whether each of those  are allocated or free.
		- Ha placing multiple files within the same group ensures that accessing one after the other will not result in long seeks across the disk.
- Other concepts that made the systems more usable than its predecessor "old [[UNIX]] filesystem":
	- Long filenames
	- Symbolic links

## 2. NTFS - Flexible tree with extents  

### (a) Explain the differences and use of resident versus non-resident attributes in NTFS.  

- The NTFS file system views each file (or folder) as a set of file attributes.
	- Includes attributes such as:
		- the file's name
		- its security information
		- and its data 
	- Each attribute is identified by an attribute type code and, optionally, an attribute name.
- The attributes are stored i the [[MFT]] file record.
	- **Non-resistent attributes**
		- If the size of the attributes are to big ( exeedes around 700 to 800 bytes ) some of the attributes are non-resident.
			- These nonresident attributes are allocated one or more clusters of disk space elsewhere in the volume.
			- If all the attributes can not fit into one MFT record, additional MFST records is created. 
				- And the first files MFT record points to the new location of all attribute records
	- **Resistent attributes**
		- Simply put: if all of the files attributes can fit within the MFT record

### (b) Discuss the benefits of NTFS-style extents in relation to blocks used by FAT or FFS.  

- [[FAT]] or [[FFS]] can become a victim of heavy file fragmentation, as a result of the writing of files in the first available area.
	- [[file fragmentation]]: files are in multiple non-contiguous file blocks
- [[Extents]] are contiguous range of clusters somewhere on the disk, described by a starting cluster number and a length.
	- If a files where to become fragmented, each portion is described by a separate extent record

## 3. Explain how copy-on-write (COW) helps guard against data corruption.

- Implements a “copy” or “duplicate” on files
	- If no modification has been done to the copy. 
		- The copy exists as a reference to the original file
	- Only if the file is modified in some way, does  a "real" copy get written. 
		- Which eliminates some concurrency issues, since all updates will be atomic.
# 3 Security  

## 1. Authentication  

### (a) Why is it important to hash passwords with a unique salt, even if the salt can be publicly known?  
- [[salt (cryptography)|Salt]] is random data input, that serves as an addition to the regular methods of hashing data.
- While hashed password generally are safe to store publicly, and would take a lifetime to brute-force, there are a-lot of other ways to identify the password.
	- With huge datasets of commonly used passwords, and cracked password using the same hash functions its not hard logically finding the school-issued.
	- Actually i managed to get my school-issued computers admin password back in high school using a rainbow table and a guide on a forum. The password was "polaroid". If i remember correctly it took me a day to figure it out. 
		- Using some publicly known salt in this case, would make it impossible to "crack the code" because of the increased table size.
	- Salt can be utilised so that the user has no idea that it is implemented, and keeping the password short and simple.


### (b) Explain how a user can use a program to update the password database, while at the same time does not have read or write permissions to the password database file itself. What are the caveats of this?

- While the user might not have permissions to read or write to the database, a program might. If the executable file has the setuid or set git attributes, then any user will be able to execute the file with root permissions. 
- This allows the system administrator or designer to give access to programs, that permits changes to system files, for the users.
- The negative aspects of this are the security and reliability issues that might occur. One must decide on giving these permissions wisely..

## 2 Software vulnerabilities

### (a) Describe the problem with the well-known gets() library call. Name another library call that is safe to use that accomplishes the same thing.

- gets() is prone to get a buffer overflow, as a result of storing the string input into the buffers without any bounds checking. In the case of gets() it keeps on reading until it sees a newline character '\\n' 
- Causing a buffer overflow is a well known security exploit. By causing the overflow, it is possible to write data in other areas on the system. 
	- On most systems the memory layout is defined, and knowing the pointers to where executable code lies - opens up for replacing it with malicious code.


### (b) Explain why a microkernel is statistically more secure than a monolithic kernel.

- The **microkernel** is a lightweight (12 000 lines of code) type of kernel which provides the bare amount of functionality that would be needed to implement an OS. This means that the user and kernel services are implemented in separate address spaces on the supervisor.
- In the **monolithic** kernel however the entire OS run as a single program in the supervisor, which means that all the user and kernel services are implemented in the same address space.
- In short, one can say the size of the kernel code, is directly linked to how vulnerable the kernel is. A smaller kernel reduces the amount of privileged code, which improves the systems security.

Am i using the word supervisor correct in this case? I feel like the word gave more sense than kernel mode.