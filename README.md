ContactGen
==========

A java app for making very realistic looking, but also very fake, people. It uses actual statistical
data (freely available online) to make these fake people as realistic as possible. For example, a
person's age is determined by the current spread of population by age, then a gender is chosen (an
equal 50/50 split right now), and then based on their age and gender along with the statistical
likelihood of first names by decade and gender, an appropriate first name is chosen. Stuff like that.

But Why?
--------

It seems like at every company I've ever worked for, I've needed at some point to make fake
people for testing. I've seen and used various methods, from just making up a small group of people,
to obfuscating the sensitive bits of actual production data. I go for some kind of in-between here:
people that look real but that (hopefully) aren't. I suppose there's some statistical likelihood that 
(assuming this thing works well) one of my fake people will bear an unfortunate resemblance to some
actual person, but so far I'm writing that off is highly unlikely, and even if so, such a person would
be buried in massive heaps of fakery. I'm probably actually doing these people a favor. Nevertheless, 
I might eventually put some effort into using some amount of intentionally incorrect data, like
phone numbers that are guaranteed to be invalid, that sort of thing.

Running It
----------

The app requires Gradle 4.10 and Java 11. Currently to run the app, you'll need to check it out of
Github, import the project via the Gradle file, and then run Main.java. Right now it just spits out a
list of 100 random people.

Things To Do
------------

* SSNs (there are standard rules for these, but it might be good to ensure they're always invalid, but
still realistic-looking)
* Race/ethnicity? The last-name data has percentage-by-ethnic-group which could make this particularly
interesting.
* The gender data is strictly binary -- it'd be nice to expand this.
* The only cities used are the most populous in each respective state -- it'd be nice to have small-town
support.
* Document where all the statistics files came from, and how to re-retrieve them in the future

