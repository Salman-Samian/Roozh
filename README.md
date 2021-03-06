# Roozh for Java
Roozh (means *day* in Kurdish) is a Java library for converting Gregorian date time to Persian date time and vice versa.

> Tired of struggling with Persian dates in Java? Does your next project needs Persian dates instead of Gregorian ones? Are you an Iranian? You know that Java doesn't support Persian dates natively.

If these questions seem familiar, the Roozh is the one here can help you achieve your goals.

**Never** waste your time again to search for a library or making new one.

## A Quick Overview What's In
* The easiest possible integration
* Integrate in less than 5 minutes
* Compatible down to Java 1.7
* Quick and simple API
* **Formatter**
* Tested and stable
* **~16KB**

## The Algorithm
This algorithm is presented by [Kazimierz Borkowski](http://www.astro.uni.torun.pl/~kb/personal.html) and can be found [here](http://www.astro.uni.torun.pl/~kb/Papers/EMP/PersianC-EMP.htm) and it's publishing is authorized by it's author.

For dates after 1799-03-20 and before 2256-03-20, the output generated by this algorithm is the same as internal Persian calendar of .NET framework. But beyond this region, there is a one day difference. To decide which is correct I am working on a code which represents the algorithm introduced by Professor Ahmad Birashk. I'll include that code into this repository too. So far from tests by sampling especially over leap years, beyond the mentioned region .NET Persian calendar is different than both other algorithms - by Borkowski and Birashk; which means it is very possible that .NET Persian calendar provides wrong dates before 1799-03-21 and after 2256-03-19.

True story is, I've found this code among some of mine from 11 years ago from beta days of .NET!

But 456 years is a wide enough range to start using this code right now for daily usage. I do not know about you, but I highly doubt that if I could see 250 years from now!

## Include to Project
### Put `.jar` File into Libs Directory
Get the provided latest compiled version of the library from `output` directory.

### Clone or Download `.zip` file
Clone this repository or download the compressed file, then extract to your computer. Simply import the library module to your project.

## Usage
```java
RoozhFormatter formatter = new RoozhFormatter()
	.appendDayOfMonth(false)
	.appendSpace()
	.appendMonthFullName()
	.appendSpace()
	.appendYear(false)
	.appendSpace().appendSlash().appendSpace()
	.appendHour(true)
	.appendColon()
	.appendMinute(true)
	.appendColon()
	.appendSecond(true)
	.appendColon()
	.appendMillisecond()
	.appendSpace()
	.appendAmPm();
// Format with current time
String formatted = formatter.build();
```

## Credits
- Kaveh Shahbazian - [Github](https://github.com/dc0d)
    - For the base of the Roozh for Java

## Developed By
- Alireza Eskandarpour Shoferi
    - [Twitter](https://twitter.com/enormoustheory) - [aesshoferi@gmail.com](mailto:aesshoferi@gmail.com)

## License
    Copyright 2016 Alireza Eskandarpour Shoferi
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
		http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.