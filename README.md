NOTE TO ANYONE VISITING THIS SITE: I started this project on January 23, 2020. Therefore, this is still a work on progress. Thank you for coming by!

Quick Background on my Android Development Journey:

In late November of 2019, I started "Developing Android Apps with Kotlin"⁠ on Udacity; it's a two-month-long course developed by Google itself. Two appropriate months later, I had finished the course and have published a personal project (Recipe Searcher) in the Google Play Store. My desire to work out my Android development muscles is still strong, so that brings us to next project!

Inspiration for this Project:

I recently read "The Happiness Advantage: The Seven Principles That Fuel Success and Performance at Work" by Shawn Achor, a Harvard lecturer who studies the link between happiness and success.  He states that it is happiness that leads to success, not vice versa. One of the book’s lessons is that our perspectives can be trained. 

The book provides an example of a Harvard study that paid 27 people to play Tetris for hours, three days in a row. The results? Participants said days after the study, their minds “literally couldn’t stop dreaming about shapes falling from the sky.” Others “couldn’t stop seeing these shapes everywhere, even in their waking hours.” This condition came to be known as the Tetris Effect by gamers. This study shows that the more people practice looking for patterns, the more it affects their way of viewing reality. As Shawn Achor puts it: “The Tetris Effect isn’t just about video games; it is a metaphor for the way our brains dictate the way we see the world around us.” 

This could mean people who tend to complain are training their brains to overlook the positives for the negatives. An excellent real-life scenario would be a software developer searching for bugs in a program, which could lead to training their brain to look for problems in everything else, like in relationships, friendships, etc. Because of this book, I was inspired to make this app in order to practice gratitude more often. Because the more positive the mind becomes, the more good you’ll see in the world!

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
