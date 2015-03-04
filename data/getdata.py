import os

sources = {
        "ministries": 6,
        "legislative": 1,
        "universities": 8,
        "judiciary": 1,
        "crown": 2,
        "electricity": 6,
        "colleges": 3,
        "hospitals": 5,
        "other": 3 }

urlPattern = "http://www.fin.gov.on.ca/en/publications/salarydisclosure/pssd/orgs.php?pageNum_pssd=%d&organization=%s&year=2013"
for cat, N in sources.items():
    for n in range(N):
        url =urlPattern % (n, cat)
        bashCommand = ('wget -O - "%s" >> output.txt' % url)
        os.system(bashCommand)
