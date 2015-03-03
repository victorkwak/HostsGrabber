HostsGrabber
============
**HostsGrabber** pulls and compiles several hosts files from the Internet as well as lists used by Ad-Block Plus to
supplement those hosts files. The program automates the process of locating up-to-date and reputable hosts files for
use with ad-blocking, as well as implementing the files into your system.
Currently, the program only supports the Mac and Linux platforms. Windows support underway!

Blocking Ads via Hosts File
---------------------------
The hosts file in a computer acts essentially as a local DNS server. Your computer will do a DNS lookup in the hosts
file before asking a remote DNS server, and if it gets a match, uses the routing information present in the local
hosts file. The functionality of the hosts file gives way to an efficient implementation of a system-wide ad-blocking
method by simply not connecting to ad-servers listed in your hosts file. There are both advantages and disadvantages to
this method of ad-blocking when compared with commonly used ad-blocking browser extensions such as Ad-Block Plus:

### Pros

+ CPU/Memory efficient. Browser extensions regularly uses large amounts of resources where the hosts method requires negligible overhead.
+ Speed. Your browser is going to ping your computer for DNS information for ad-servers vs pinging a remote server. Along with the using less system resources, your overall browsing experience should be faster than if using an ad-block extension.
+ Battery life. Less usage of system resources should lead to more battery life. To provide an anecdote, I get approximately one more hour of battery life from my laptop from not using an ad-blocking extension. Results will vary, of course.

### Cons

- Less thorough. While the hosts file can only block out entire domains, browser extensions can use wildcard searches as well as block out specific elements of a website and will overall be more effective at eliminating ads from your view.
- Sometimes breaks functionality. When you block an entire domain, sometimes certain functions don't work when other websites depend on that domain. This is rectified by using a whitelist.

### TODO

1. Windows support.
2. Move logic to JavaFX GUI.
3. Whitelist.
4. Custom Add list.
5. View hosts file.
6. Be able to choose hosts file sources.
7. Backup original and current hosts file
