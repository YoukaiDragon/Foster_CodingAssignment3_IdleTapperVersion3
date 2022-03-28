# Coding Assignment 3 - Idle Tapper Version 3

Idle Tapper Version 3 is a further upgrade to Idle Tapper, an incremental game where the user clicks on a button to gain "taps" which are spent
to increase the amount of taps gained per click of the button, as well as passivly per second.

This version has been changed to use a ViewModel and a Room database to store data, allowing the users progress to persist even when they turn the app off.
I have also added a "prestige" function, where the user can reset their taps and upgrades to gain a multiplier on the number of taps earned
based on the number of taps they had when they "prestiged", which stacks with each successive reset. I also added a button that allows the user to fully
reset their progress if they so choose. Both the prestige and reset buttons also have confirmation dialog via alerts to help prevent the user from
activating either function by accident.

The archetecture of the app was also changed compared to Version 2. Now, instead of two different Activities, there are two fragments hosted by the same
activity, along with the ViewModel that both fragments use to access and update the data used by the app.

As for open issues, there is something that was technically resolved, but not in the way I had originally planned. I had wanted to display the store upgrades
using a RecyclerView, like I had in Version 2, but for some reason I was having trouble getting it to display properly in a fragment. In the end, due to time,
I made the decision to just manually implement the store buttons, rather than use the RecyclerView. While I don't think this impacts the performance of the
app in it's current state, as there are not enough store buttons to require any amount of scrolling, it require lengthier code in a couple of sections pertaining
to the upgrade functionality. 

There were also a couple of other issues, namely that the idle tapping function would sometimes not activate at a rate of 1/second,
and sometimes when closing the app in the emulator and relaunching it, I got a message saying "Pixel launcher isn't responding". That second error didnt' seem
to actually interfere in the operation of the app though, and both errors may be due not to any error with my code, but with how slow my laptop runs while the
android emulator is active.
