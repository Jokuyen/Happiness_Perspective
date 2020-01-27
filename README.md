## Note for Website Visitors

I started this project on January 23, 2020. Therefore, this is still a work on progress. Thanks for coming by!

### My Android Development Journey

In late November of 2019, I started "Developing Android Apps with Kotlin"⁠ on Udacity; it's a two-month-long course developed by Google itself. Two appropriate months later, I had finished the course and have published a personal project (Recipe Searcher) in the Google Play Store. I do want to keep to growing my Android Development muscles, so that brings us to this next project!

### Inspiration for this Project

I recently read "The Happiness Advantage: The Seven Principles That Fuel Success and Performance at Work" by Shawn Achor, a Harvard lecturer who studies the link between happiness and success.  He states that it is happiness that leads to success, not vice versa. One of the book’s lessons is that our perspectives can be trained. 

The book provides an example of a Harvard study that paid 27 people to play Tetris for hours, three days in a row. The results? Participants said days after the study, their minds “literally couldn’t stop dreaming about shapes falling from the sky.” Others “couldn’t stop seeing these shapes everywhere, even in their waking hours.” This condition came to be known as the Tetris Effect by gamers. This study shows that the more people practice looking for patterns, the more it affects their way of viewing reality. As Shawn Achor puts it: “The Tetris Effect isn’t just about video games; it is a metaphor for the way our brains dictate the way we see the world around us.” 

This could mean people who tend to complain are training their brains to overlook the positives for the negatives. An excellent real-life scenario would be a software developer searching for bugs in a program, which could lead to training their brain to look for problems in everything else, like in relationships, friendships, etc. Because of this book, I was inspired to make this app in order to practice gratitude more often. Because the more positive the mind becomes, the more good you’ll see in the world!

### Features
- Room (implements SQLite)
- MPAndroidChart
- RecyclerView
- Floating Action Button Extended

### Challenges
- RecyclerView: Too difficult to implement a second type of ViewHolder
- MPAndroidChart: Learning to use the library
- Room: Lack of date format

### Notable Bugs

#### Mysterious Crashing (Spoiler: Old Database Version)
At one point, I added new properties to the Entity class for my database. Because SQLite lacked an actual format for dates, I decided to added individual columns for the year, month, and day.

After the change, I tried to submit a new entry. Without explanation, the app crashed before reaching the CurrentMonth fragment. For an hour, I tried to figure out what the bug could be. I tried debugging and looking at the Logcat, but that didn't help much. I had no lead.

Then it occured to me that the current database was currently holding the old Entity version, which might somehow interfere with the current version of the Entry class. Therefore, I concluded that this could be a database bug. 

My theory was that if I deleted the old database and started a fresh one, the mysterious crashing would stop. So, I went ahead and updated the version of the database from 1 to 2.

And like that, my app stopped crashing! Words truly cannot express how magical of an experience that was for me, if I'm being frank.

I fear where my mental health would have ended up had I not guessed upon this solution. For other newbies working with Room, make sure to check on your database after changing the properties of an Entity. Otherwise, you might end up with your app crashing. (And not initially understanding what went wrong like I had!)

#### mutableMapOf<Int, Int>().withDefault{0}
In ProgressFragment, I wanted to display the chart with each month's entry count. However, it kept coming up with an empty chart. That led me to think there was an issue with the database access in terms of asynchronous queries (meaning that the chart was retrieving from  an empty variable before the app was done accessing the database). 

Very long story short (as in, condensing two full days worth of debugging and headaches into a few paragraphs), it turns out that the database access was fine. It was just the map I was using to keep track of each entry per month. I had used "val monthCount = mutableMapOf<Int, Int>().withDefault { 0 }" with the idea that if I were to retrieve a value from a key that hadn't been used yet, I would get 0. However, after eventually focusing my debugging on the map, I learned that wasn't actually the case. When I directly accessed the map with indexing (monthCount[index]), I would get a null! Only God knows how equally relieved and irked I became because of such a trivial bug. After doing some research on Kotlin's mutableMapOf on Google, I saw that someone else had noticed the same thing with this data structure. The provided solution I found was not to access the map with indexing but instead with a function (monthCount.getValue()). That way, if the value was null, the function would return the expected default of 0. 

To sum it up, it was a map access issue. Before I discovered this bug, I had been using nulls instead of actual Ints to keep count the entries of each month. After changing from monthCount[index] to monthCount.getValue(index), I was no longer being returned an unexpected null.

I'm definitely adding this bug to my Hall of Fame.
