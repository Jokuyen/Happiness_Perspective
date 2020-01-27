About Me:
In late November of 2019, I started "Developing Android Apps with Kotlin"⁠ on Udacity; it's a 2-month-long course developed by Google itself. Fast forward 2 months later, I've had completed the course and published a personal project in the Google Play Store. My desire to work out my Android development muscles is still strong, so that brings us here!

There's this book called "The Happiness Advantage" by Shawn Achor; it states that happiness results in success, not vice versa. One lesson  I found interesting is that our perspectives can be trained. For example, hardcore Tetris players will begin to search for shapes in the real world after hours of playing. This could mean that people who tend to complain a lot are training their brains to look for the bad. An excellent example would be a software developer searching for bugs in a program, which could lead to looking for problems in the real world, like in relationships, friendships, everything. This means software developers are more prone to finding problems first instead of what's actually working. Therefore, I was inspiried to make this app in order to practice gratitude; the more you look for the good, the more positive the mind becomes.

Features:
- Room (implements SQLite)
- MPAndroidChart
- RecyclerView
- Floating Action Button Extended

Challenges:
- RecyclerView: Too difficult to implement a second type of ViewHolder
- MPAndroidChart: Learning to use the library
- Room: Lack of date format

Lesson from mutableMapOf<Int, Int>().withDefault { 0 }:
In the ProgressFragment, I was trying to display the chart of each month's entry count. However, the chart kept stating that it had no data to show. That led me to think there was an issue with the database access in terms of asynchronous queries (meaning that the chart was using the \_entries data before the program was done retrieving from the database). 

Very long story short (as in condensing 2 full days worth of debugging and headaches into this paragraph), it turns out that the database access was fine. It was just the map I was using to keep track of each entry per month. I had used "val monthCount = mutableMapOf<Int, Int>().withDefault { 0 }" with the idea that if I were to retrieve a value from a key that hasn't been used yet, I would get 0. However, after focusing my debugging on the map, I learned that wasn't the case. When I directly accessed the map with indexing (monthCount[index]), I would get a null! Only God knows how equally relieved and irked I became. After researching Kotlin's mutableMapOf data structure on Google, I saw that someone else had spotted the problem. The solution was to not access the map with indexing but with a function (monthCount.getValue). That way, if the value was null, the function would return the default of 0 now. 

To sum it up, it was a map access issue. After changing from monthCount[index] to monthCount.getValue(index), I was no longer being returned an unexpected null.

I'm definitely adding this bug to my "Hall of Infamous Trivial Bugs." Fortunately for me, it's rare that this hall gets a new addition to its collection.

Lesson from a mysterious crash:
I changed my Entry data class to include year: Int, month: Int, and day: Int. 

After the change, if I tried to submit a new Entry, the app crashes before reaching the CurrentMonth fragment. For an hour, I tried to figure out what the bug could be. I tried debugging and looking at the Logcat, but that didn't help much. 

Then it dawned on me that the database's table was currently holding the old Entity version, which might somehow interfere with the current version of the Entry class. Therefore, this could be a database bug. 

My theory was that if I deleted the old database and started fresh, this bug would go away. So, I went to clear the data using the clear() method of the Dao. Then, I updated the version of the database. 

And like that, my app stopped crashing! It felt like a magical experience, to be frank.

I fear where my mental health would have ended up had I not guessed upon this solution. For anyone working with Room, make sure to check on your database after changing the class of your chosen Entity. Otherwise, you might end up with your app crashing. (And not understanding what went wrong like I did!)
