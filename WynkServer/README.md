HttpServer
----------

You’ve just been hired in a new stealth startup, that wants to disrupt the way people waste time on the internet. The problem is there isn’t enough content, and it’s often questionable. So our VP of Product came up with this brilliant idea. Let’s build a product that does nothing except take a long time to load. This way people can waste as much time as they want without you having to deal with actually finding good content. You’ve been tasked with building v1 of this hacker news and every other major news site on the first day that they launch so they have asked you to ensure that your design is as performant on a single box as possible.

Here are the three HTTP requests you need to implement

__GET /sleep?timeout=20s&connid=1__<br>
should sleep for 20 secs then return a proper 200 HTTP response with a body of {“stat”:ok}

__GET /server-status__ <br>
should return a table that shows all the current connections and how much time they have left to sleep (so 5 seconds after I make the first sleep request, server-status would have 1 connection with 15 seconds left to sleep)

__POST /kill__ <br>
with arguments <br>
__connid=1__ <br>
returns immediately, and also causes the connection with connid=X to return immediately with response {“stat”:killed}. The response to this request should be {“stat”:ok} (unless the request wasn’t found in which case you should decide what the most appropriate response is.
<br>

The last catch is that your VP of Engineering doesn’t believe that any HTTP library will be as performance as one that you write yourself (you really need a new job) so has forbidden you from using one. So be prepared to read/write from sockets yourself and to parse/prepare the responses yourself. You can use json libraries.

In conclusion the 3 major requirements are 
Implement the above APIs
As efficiently as possible (be prepared to discuss techniques and your choices)
Without using an HTTP library

You may use any language you want, but bear in mind that if it’s archaic and I don’t know it it will be difficult to evaluate the quality of code. For reference, ht HTTP RFC is google-able (search for HTTP RFC)
