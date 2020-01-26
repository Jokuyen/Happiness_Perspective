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

Lesson from a mysterious crash:
I changed my Entry data class to include year: Int, month: Int, and day: Int. 

After the change, if I tried to submit a new Entry, the app crashes before reaching the CurrentMonth fragment. For an hour, I tried to figure out what the bug could be. I tried debugging and looking at the Logcat, but that didn't help much. 

Then it dawned on me that the database's table was currently holding the old Entity version, which might somehow interfere with the current version of the Entry class. Therefore, this could be a database bug. 

My theory was that if I deleted the old database and started fresh, this bug would go away. So, I went to clear the data using the clear() method of the Dao. Then, I updated the version of the database. 

And like that, my app stopped crashing! It felt like a magical experience, to be frank.

I fear where my mental health would have ended up had I not guessed upon this solution. For anyone working with Room, make sure to check on your database after changing the class of your chosen Entity. Otherwise, you might end up with your app crashing. (And not understanding what went wrong like I did!)
