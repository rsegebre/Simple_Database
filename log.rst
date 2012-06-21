LOG:
####

Day 1:
======
**15:20:** So I decided to do assignment # 3, because it fits my current skills and I found the most interesting out of the three. 
I started off by reading the first part a couple of times to make sure I have a good grasp of everything, then move on the three additional
commands that need to be built in.

**15:30:** I started laying out the structure of the program to handle various commands and it is going to be divided into the following:
Command ->class that encompasses each command as a string and the values each command takes as a dictionary
Database -> contains the datastructure that will store all the info and provide access to its elements.
Processor->takes care of performing the operation of the command given
Input_Handler-> takes user input and puts everything together... contains main.

**17:30:** I finished doing most of the first part of the database code, I tried doing without thinking that much about efficiency, then afterwards
I am going to make it more efficient. I've read that this is a more common practice, to get things working first and then focus on efficiency. But 
not to take it completely out of the equation when you are writing code the first time because you don't want to have to change everything in you code
to improve its running time.

**17:53:** I've debugged the first part and it works just fine. Main issues I faced was when overwriting a variable using SET, I didn't update the
reverse hashmap that keeps the count of values to number of time it shows up. Besides this I didn't have any other major bugs. I'm going to start working on the rest tomorrow.

Day 2:
======

**13:09:** I re-read the second part of the database and my first idea is a stack, every time we get a new BEGIN we start storing things in a new 
empty database, if we get another begin we push that database into a stack and use a new empty database, and repeat. If commit, we start...

I've been playing around with github and eclipse for a while as you can tell... finally got it setup so that I can simply push code from eclipse with
a simple click!

Day 3:
======

**16:22:** I started working on the transaction part of the database. I was thinking of storing every instruction but in the specifications it says that
I shouldn't go crazy with the memory usage, so duplicating my database and throwing in a stack sounded too easy. So after trying to store changes to database
separatedly... I finally went with storing the opposite/reverse of a command (i.e BEGIN, SET a 10, SET a 22, ROLLBACK, END/ I would store UNSET a, SET a 10).

**16:42:** I was thinking on why I failed when trying to store the intermediate states of the database, and it was because by storing partial information,
it became more complicated to restore a previous state to my database when a ROLLBACK was registered. In particular, I would've had to fulfill
any commands using information from the previous state in the database and reconcile those commands with the new partial information stored in the database.
Storing the opposite/reverse of a command is way simpler, each transaction contains the opposite of a command... upon rollback you simply run the commands. :D

**17:51:** Found some bugs:
 
- Null Integer parse conversion exception cause program to close... 
- ConcurrentModification Exception: regulate access to global variables.
- Removed extra classes, processor class was just fluff
- Pushed functionality towards database

Day 4-6:
========

I didn't do much, I got somewhat sidetracked with a side project...(setting up my website, using pelican) check it out at rsegebre.com_ . Simple but it does the job, and besides it is pretty cool because everything is generated statically and I just have to push it to github :D

Day 7:
======

**13:00:** Started running the tests provided on the Thumbstack website. Which took me some time since I haven't done any JUnit testing in a while. But when done it is pretty sweet, any tests doesn't pass, you can just debug it individually, throw some breakpoints and go into debug mode on eclipse, fixed most bugs pretty quickly.

**14:00:** All tests passed! Now I gotta do some dumb_testing, for invalid inputs. I also want to make the code such that it doesn't stop executing once it finds an invalid input it just keeps going until it hits an END command.

**16:00:** I got bored so I took some time off watched some TV and generated some of the JavaDocs Documentation meanwhile. Time for a git push.





.. _rsegebre.com: http://rsegebre.com