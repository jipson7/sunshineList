###Java.Swing application to graphically view the Sunshine List of Ontario

This project is done as an assignment for csci2020u at UOIT

See the [course site](http://leda.science.uoit.ca/teaching/sysdev/assignments/assignment3) for more details.

####Use:

The `Top` class is an executable command line tool to query the sunshine list. It sorts the list by category and prints the top results. Its use is as follows.

```
java Top <html-file> <k> <sectors|employers|positions|names>
```

with parameters:

* <html-file> - The location of the raw html that was pulled with getdata.py
* <k> - Include the top "k" names on the list
* <sectors|employers|positions|names> - The category to query
