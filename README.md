I've modernized the [original](http://xp123.com/articles/test-first-challenge/) Test First Challenge using Gradle!

Now you can simply generate an IDE project: (if you're using Windows replace gradlew with gradlew.bat)

For IDEA:

* gradlew idea

For Eclipse:

* gradlew eclipse

Open up your IDE and write the code you need to pass the tests (either in Groovy or Java). Once you have the tests
passing the Gradle script will the next test case for you:

* gradlew next

Enjoy!

PS The project will start in a state in which it won't compile. You'll need to add a Sheet class to either src/main/groovy or src/main/java first.
PPS Please provide feedback [here](http://www.curiousattemptbunny.com/2011/04/test-first-challenge-modernized-for.html).
