### Google Play Store
[Google Play Store](https://play.google.com/store/apps/details?id=com.jokuyen.happinessperspective)

### About the App
I started with an idea:

![Whiteboard Sketch](/images/whiteboardDraft.jpg)

Which resulted to this after 2 weeks:

![Navigation Graph](/images/navigationGraph.png)

![Home Screen](/images/homeScreen.jpg)

![Navigation Drawer](/images/navDrawer.jpg)

![Progress Chart](/images/progressChart.jpg)

![New Entry Screen](/images/newEntryScreen.jpg)

![Date Picker](/images/datePicker.jpg)

![Entire Year](/images/entireYearScreen.jpg)

![Entry Details](/images/entryDetailsScreen.jpg)

![Settings](/images/settingsScreen.jpg)

### Features
- Room (implements SQLite)
- MPAndroidChart
- RecyclerView + Cards
- Material Design Components

### Challenges I Faced
- Android Development: Tremendously steep learning curve!
- RecyclerView: Too difficult to implement a second type of ViewHolder
- MPAndroidChart: Learning to use the library
- Room: Lack of date format
- Unit Testing: LiveData

### My Android Development Journey

In late November of 2019, I started "Developing Android Apps with Kotlin"⁠ on Udacity; it's a two-month-long course developed by Google itself. Two appropriate months later, I had finished the course and have published a personal project (Recipe Searcher) in the Google Play Store. Now that I've had a taste of the experience, I want to continue growing my Android Development muscles. So, that brings us to this next project!

### Inspiration for this Project

I recently read "The Happiness Advantage: The Seven Principles That Fuel Success and Performance at Work" by Shawn Achor, a Harvard lecturer who studies the link between happiness and success.  He states that happiness leads to success, not the other way around. 

One of the lessons in his book is that our perspectives can be trained. For example, there was a Harvard study that paid 27 people to play Tetris for hours, three days in a row. The results? Days after the study, the participants said their minds “literally couldn’t stop dreaming about shapes falling from the sky.” Other participants “couldn’t stop seeing these shapes everywhere, even in their waking hours.” 

In an extreme case, a Tetris addict shared a similar experience in the *Philadelphia City Paper*: "Walking through the aisles at the local Acme, trying to decide between Honey Nut or the new Frosted Cheerios, I notice how perfectly one set of cereal boxes would fit in with the gap on the row below it. Running doggedly around the track at the Y, bored out of my mind, I find myself focusing on the brick wall and calculating which direction I'd have to rotate those slightly darker bricks to make them fit in with the uneven row of dark bricks a few feet lower down the wall. Going out to get some fresh air after hours of work, I rub my watery, stinging eyes, look up at the Philadelphia skyline, and wonder, 'If I flip the Victory Building on its side, would it fit into the gap between Liberties One and Two?'"

Such an odd condition came to be known as the Tetris Effect. This study shows that the more people practice looking for patterns, the more it affects their way of viewing reality. As Shawn Achor puts it: “The Tetris Effect isn’t just about video games; it is a metaphor for the way our brains dictate the way we see the world around us.” 

This could mean people who tend to complain are training their brains to overlook more of the positives for the negatives; there's a saying that the more you practice something, the easier it becomes. An excellent real-life scenario would be a software developer searching for bugs in a program, which could lead to training their brain to look for problems in everything else, like in relationships, friendships, themself, etc. Because of this book, I was inspired to create this app in order to practice gratitude more often (and to flex the Android Development muscles, of course). I wouldn't be surprised if there are already a bunch of similar apps on the market, but the least I could do is bring more awareness to the importance of gratitude. The main takeaway I want you to realize is that the more positive the mind becomes, the more good you’ll notice in the world!

### Notable Bugs

#### Mysterious Crashing (Spoiler Alert: Old Database Version)
Because SQLite lacked an actual type for dates, I was using the TEXT type to hold them. But then, I had to retrieve entries from the database and realized I would have to parse the string to manually extract the year, month, and day. So I decided to add the year, month, and day as an Int type to my Entity class.

After the change, I tried to submit a new entry. Without explanation, the app crashed before reaching the CurrentMonth fragment. For an hour, I tried to figure out what the bug could be. I tried debugging and looking at the Logcat, but that didn't help much. I had no lead.

Then it occured to me (out of nowhere, I should say) that the current database was working with the old Entity version, which might mean it would have an issue with inserting the new version of the Entity. Therefore, I concluded that this could be a database bug. 

My theory was that if I deleted the old database and started a fresh one, the mysterious crashing would stop. So, I went ahead and updated the version of the database from 1 to 2, which Room handled by starting a new database.

And just like that, my app stopped crashing! Words truly cannot express how magical of an moment that was for me, if I'm being frank.

I fear where my mental health would have ended up had I not guessed upon this solution. For other newbies working with Room, make sure to check on your database after changing the properties of an Entity. Otherwise, you might end up with your app crashing. (And not initially understanding what went wrong like I had!)

#### mutableMapOf<Int, Int>().withDefault { 0 }
In ProgressFragment, I wanted to display the chart with each month's entry count. However, it kept coming up with an empty chart. That led me to think there was an issue with the database access in terms of asynchronous queries (meaning that the chart was retrieving from  an empty variable before it could finish being filled with data from the database). 

Very long story short (Translation: Condensing two full days worth of debugging and headaches into a few paragraphs), it turns out that the database access was fine. The issue was the map data structure I used to keep track of each entry per month. I had used "val monthCount = mutableMapOf<Int, Int>().withDefault { 0 }" with the idea that if I were to retrieve a value with a key that hadn't been used yet, I would get 0 and increment it to 1. However, after eventually focusing my debugging on the map data structure, I learned that wasn't actually the case. When I directly accessed the map with indexing (monthCount[index]), I would get a null! Only God knows how equally relieved and irked I became because of such a trivial bug. After doing some research on Kotlin's mutableMapOf on Google, I saw that [someone else had noticed the same thing with this data structure](https://discuss.kotlinlang.org/t/map-withdefault-not-defaulting/7691). I mean, you would think that if you initialized a map with a default value, you shouldn't be getting a null. But that's exactly what was happening. The provided solution I found was not to access the map with indexing but instead with a function (monthCount.getValue()). That way, if the value was null, the function would return the expected default of 0. 

To sum it up, it was a map access issue. Before I discovered this bug, I had been using nulls instead of actual Ints to keep count the entries of each month. After changing from monthCount[index] to monthCount.getValue(index), I was no longer being returned an unexpected null. 

I'm definitely adding this sneaky bug to my Hall of Fame.

Afternote: I realized a simpler solution would be using an Int array of size=12 to track the months instead of a map. Nonetheless, this was a good learning experience in spite of the total frustration!

### Moments of Accomplishment

#### Practicing SQL Knowledge

#### Using Previous Experience of Graphing to Learn MPAndroidChart

#### Tried Unit Testing

#### Design Decisions: Learning about Depedency Injection, but deciding using Singleton would be better for my project

#### Used Chips for Filtering

#### Learned more about LiveData and Transformations.switchMap
