## Chapter 10 - The network project 
### 10.1 - Create a news feed with messages and photos
```java
 NewsFeed feed = new NewsFeed();
        feed.addMessagePost(new MessagePost("Alice", "Hello!"));
        feed.addPhotoPost(new PhotoPost("Bob", "photo.jpg", "A photo"));
        feed.addMessagePost(new MessagePost("Charlie", "I'm in New York today! Anyone want to meet up?"));
        feed.addPhotoPost(new PhotoPost("David", "selfie.jpg", "Look at my new selfie!"));
        feed.addMessagePost(new MessagePost("Eve", "I'm so excited about the new Social Network course!"));
        feed.show();
```

### 10.2 - Add comments
The post will need to be added to the object before adding to the array. To avoid this one would have to create a method for adding the comment to the post object in the array in newsfeed.

```java
NewsFeed feed = new NewsFeed();
        MessagePost msgPost = new MessagePost("Eve", "I'm so excited about the new Social Network course!");
        msgPost.like();
        msgPost.addComment("Great post bruh!");
        feed.addMessagePost(msgPost);
        feed.show();
```

### 10.3 - Draw an inheritance hierarchy

![[Inheratiance Hierarchy]]

### 10.4 - removing extends Post
The MessagePost class does not have a realtionship with Post anymore
![[Pasted image 20230220163816.png|300]]

### 10.5 - Create a MessagePost object
Works like a charm
```java
// main method: in MessagePost

MessagePost post = new MessagePost("Marge", "Hello, world!");
post.addComment("bruh moment");
post.display();
```

### 10.6 - Access private elements of superclass
Add a acessor in superclass:
```java
public String getUserName() {
        return username;
    }
```
Add a method in subclass:
```java
public void printShortSummary()
    {
        System.out.println("MessagePost from: " + getUserName());
    }
```

### 10.7 - Debugging
Stepping into `super(author)` in MessagePost shows that the intancefield, `username` in the Post constructor is `username = null` and and that `author = "Stian"`.

The username is then set to `"Stian"`.

### 10.9 - Order items into an inheritance hirearchy
![[Øving 2 - IDATT2001 - A2 Teoriprøve 2023-02-20 17.15.34.excalidraw]]

### 10.10 - Inhertiance relationship between mouse and touchpad
```
		 mouse
	      /  \
	  mouse  touchpad
```

### 10.11 - relationship rectangle & square
A square can have many shapes such as rectagles. On the other hand a rectagle is also a square

### 10.12 - true/false
- `Teacher` & `Student` are subclasses of `Person`.
- `PhDStudent` is a subclass of `Student`.

**a.** Which is legal
- `Person p1 = new Student();`  - True
- `Person p2 = new PhdStudent();` - True 
- `PhDStudent phd1 = new Student();` - False
- `Teacher t1 = new Person();` - False
- `Student s1 = new PhDStudent();` - True

### 10.13 - test previous question.
- In github repo: `/øving2/BareBoneClasses`

### 10.14 - What needs to be changed to allow EventPost & CommentPost?
- Changes in repo: `/øving2/network-v2/`
- Yanked likes and comment fields and methods from `Post` and put it in `CommentedPost` 
- Changed private methods that has to be acessed to public

### 10.15 - Java standard Collection Hierarchy
![[Pasted image 20230220180825.png]]

# Quiz tasks: 1,2,3,4,5,6,7,8
## Gitt følgende klassediagram som viser arv mellom ulike klasser:
![[Pasted image 20230220181754.png|400]]

### 1 - `Rabbit r = new Rabbit();`
- True

### 2 - `Animal a = new Rabbit();`
- True

### 3 - `Rabbit r = new Animal();`
- False

### 4 - `Actor a = new Fox();`
- True

### 5 - Hvilke nøkkelord brukes for å referere til metoder i klassen det arves fra ?
- `super`

### 6 - Hva blir skrevet ut?
```java
public class A
 {
     private int i;
     
     public void display()
     {
         System.out.println(i);
     }
     
     public void setI(int i)
     {
         this.i = i;
     }
 }
 
 
 class B extends A
 {
     private int j;
     
     public void display()
     {
         System.out.println(j);
     }
     
     public void setJ(int j)
     {
         this.j = j;
     }
     
 }
 
 class inheritanceDemo
 {
     public static void main(String[] args)
     {
         B obj = new B();
         
         obj.setI(1);
         obj.setJ(2);
         obj.display();
     }
 }
```

- output: 2

### 8 - Alle klasser i Java arver automatisk fra en bestemt klasse.
- `java.lang.Object`

## Chapter 11 - More about inheritance

### 11.1 - Move `display()` method from Post:
- Same as answer at [[#10.14 - What needs to be changed to allow EventPost & CommentPost?]]
- New source code in github repo: `/øving2/Chapter 11 Exercises/`
- Added public accessors in `Post`

_MessagePost.java:_
```java
       /**
     * Display the details of this post.
     * 
     * (Currently: Print to the text terminal. This is simulating display 
     * in a web browser for now.)
     */
    public void display()
    {
        System.out.println(getUserName());
        System.out.print(timeString(getTimeStamp()));

        int likes = getLikes();
        ArrayList comments = getComments();
        
        if(likes > 0) {
            System.out.println("  -  " + likes + " people like this.");
        }
        else {
            System.out.println();
        }
        
        if(comments.isEmpty()) {
            System.out.println("   No comments.");
        }
        else {
            System.out.println("   " + comments.size() + " comment(s). Click here to view.");
        }
    }
```

### 11.2 - Modifying display() in `MessagePost` and `PhotoPost` :
- Result: Only prints `display()` in the subclasses.

### 11.3 - Modifying to include `super` call in the subclass methods:
```java
 public void display()
    {
        super.display();
        System.out.println(message);
    }
```

- **Results:** Now it prints the superclass statements before the subclasses:

### 11.4 - Look up `toString()` in the documentation. What are its parameters:
```md
public String toString()

Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object. The result should be a concise but informative representation that is easy for a person to read. It is recommended that all subclasses override this method.

The toString method for class Object returns a string consisting of the name of the class of which the object is an instance, the at-sign character `@', and the unsigned hexadecimal representation of the hash code of the object. In other words, this method returns a string equal to the value of:

     getClass().getName() + '@' + Integer.toHexString(hashCode())
     

Returns:
    a string representation of the object.
```

- **Parameter:** Name of the object
- **Return:** A string representation of the object.

### 11.5 - add `toString()` to `PhotoPost`

```java
 PhotoPost o = new PhotoPost("Stian", "thisIs.boring", "bruh");
System.out.println(o.toString());
```

### 11.6 - Change order of `display()` to show same as in figure 11.10
- found in the repo: `/øving2/Chapter 11 Exercises/network-v2`

### 11.7 - Change order of `display()` to show same as in figure 11.11
- todo

# Quiz tasks 9,10
### 9 - Dersom du trenger å lage en metode som ikke skal være tilgjengelig for alle andre klasser/objekter, men samtidig skal være tilgjengelig for klasser som arver fra klassen din, hvilket nøkkelord ville du da benyttet?
- Protected

### 10 - Hva gjør operatoren instanceof ?

```java
class Post
 {
     // Detalis omitted...
 }
 
 class MessagePost extends Post
 {
     // Details omitted...
 }
 
 ArrayList<Post> posts = new ArrayList<>();
 
 // Details omitted...
 
 ArrayList<MessagePost> messages = new ArrayList<>();
 for (Post post : posts)
 {
     if (post instanceof MessagePost)
     {
         message.add((MessagePost) post);
     }
 }
```
- Sjekker om objektet som post-variabelen holder på er av datatypen MessagePost

# Chapter 12 - Further Abstraction Techniques
### 12.1 - Does the number of foxes increase if `simulateOneStep` is called once more?
| Zero | Two times |
| -------- | --------- |
| ![[Pasted image 20230221001115.png]] | ![[Pasted image 20230221000653.png]]                    |

- **Answer:** no

### 12.2 - Does the number of foxes change on every step?
| One time | Two Times |
| -------- | --------- |
|![[Pasted image 20230221001011.png]]          |   ![[Pasted image 20230221000544.png]]        |

- **Answer:** Yes, it seems like there was a decline of foxes from step 0 to one. Maybe rabbits < 921 is to few, in regards to food. After the population of rabbits > 921 the fox population grew.

### 12.3 - With higher number of steps. Does foxes and rabits increase or decrease at similar rates?
| 50 Steps                             | 100 Steps                            |
| ------------------------------------ | ------------------------------------ |
| ![[Pasted image 20230221002800.png]] | ![[Pasted image 20230221002843.png]] |

- **Answer:** Unsure

### 12.4 - 
![[Pasted image 20230221003420.png]]

### 12.5 - Reset
![[Pasted image 20230221003602.png]]

### 12.6 - 
![[Pasted image 20230221003822.png]]

### 12.7 - change delay
### 12.8 - 
![[FoxAndRabbitSimulation.mp4]]

### 12.9 Randomizer class
**From java API documentation**

>  If two instances of Random are created with the same seed, and the same sequence of method calls is made for each, they will generate and return identical sequences of numbers. In order to guarantee this property, particular algorithms are specified for the class Random. Java implementations must use all the algorithms shown here for the class Random, for the sake of absolute portability of Java code. However, subclasses of class Random are permitted to use other algorithms, so long as they adhere to the general contracts for all the methods.

The algorithms implemented by class Random use a protected utility method that on each invocation can supply up to 32 pseudorandomly generated bits.

Many applications will find the method Math.random() simpler to use. 

### 12.10 - Does `useShared` fix the repeatability?
- **Answer:** yes

### 12.11 - Is gender as an attribute that matters?
- It is important on the basis of population growth.
- A low number of females means a low number of new animal children
- A low number of males matters less as they can impregnate many females. 
- The breeding ratio could possibly be wrong.

### 12.12 - Simplification that could affect the accuracy
- Sickness
- Other predators
- Low age, less likely to survive also

### 12.13 - Experiment, breeding probability
Breeding prob: 0.12
![[Pasted image 20230221131133.png]]

Breeding prob: 
![[Pasted image 20230221131333.png]]

- **Answer:** more rabbits, but also more foxes. This indicates foxes thrive when more rabbits are present.

### 12.15 - Increasing max age of foxes
- **Answer:** Actually leads to more foxes in cycles. But dies out because of lack of food. and the rabbit population grows as a result. 
	- Rabbits does not zero out

### 12.16 - Experiment with combination to achieve balance

### 12.17 - Does fielsize affect survivalrate?
![[Pasted image 20230221132621.png]]

- **Answer:** Yes, better margins

### 12.18 - field differences
![[Pasted image 20230221133040.png]]

### 12.18 - two halves on whole
![[Pasted image 20230221133421.png]]


